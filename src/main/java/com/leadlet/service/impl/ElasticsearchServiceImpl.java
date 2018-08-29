package com.leadlet.service.impl;

import com.leadlet.domain.Deal;
import com.leadlet.domain.enumeration.FacetType;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import com.leadlet.service.dto.TermsFacetDTO;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class ElasticsearchServiceImpl implements ElasticsearchService {


    private final RestHighLevelClient restHighLevelClient;
    private final DealRepository dealRepository;

    public ElasticsearchServiceImpl(RestHighLevelClient restHighLevelClient, DealRepository dealRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.dealRepository = dealRepository;
    }


    @Override
    public FacetDTO getFieldTerms(FacetDefinitionDTO facetDefinition) throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //for (FacetDefinitionDTO facet : facetDefinitions){
            addFacet( searchSourceBuilder, facetDefinition);
        //}

        searchRequest.source(searchSourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest);

        //for (FacetDefinitionDTO facet : facetDefinitions){
        FacetDTO facet =  getFacet( sr, facetDefinition );
        //}

        return facet;

    }

    @Override
    public List<Long> getDealsTerms(String query) throws IOException {
        SearchRequest searchRequest = buildSearchQuery(query);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);

        List<Long> dealIds = getDealIds(searchResponse);

        return dealIds;
    }


    private List<Long> getDealIds(SearchResponse searchResponse) {

        List<Long> ids = new ArrayList<>();

        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Long id = Long.valueOf( (Integer) sourceAsMap.get("id"));

            ids.add(id);
        }

        return ids;
    }

    private SearchRequest buildSearchQuery(String query) {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[] {"id"};
        searchSourceBuilder.fetchSource(includeFields, null);

        searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));

        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private FacetDTO getFacet(SearchResponse sr, FacetDefinitionDTO facet) {

            // sr is here your SearchResponse object
            Terms genders = sr.getAggregations().get(facet.getId());

            TermsFacetDTO facetDTO = new TermsFacetDTO();
            facetDTO.setId(facet.getId());

            // For each entry
            for (Terms.Bucket entry : genders.getBuckets()) {
                facetDTO.addTerm(entry.getKey().toString(),entry.getDocCount());
            }

            return facetDTO;


    }

    private void addFacet(SearchSourceBuilder searchSourceBuilder, FacetDefinitionDTO facet) {
        if( facet.getType().equals(FacetType.TERMS)){
            searchSourceBuilder.aggregation(AggregationBuilders
                .terms(facet.getId())
                .field(facet.getDataField()));
        }
    }


    @Override
    @Scheduled(fixedDelay = 5000)
    public void syncDeals() {

        int pageNo = 0;

        boolean getPages = true;

        while(getPages){
            Pageable pageable = new PageRequest(pageNo, 20);

            Page<Deal> dealsPage = dealRepository.findAllBySyncStatusAndCreatedDateLessThan(SyncStatus.NOT_SYNCED, Instant.now(), pageable);

            if( dealsPage.getContent() == null ||  dealsPage.getContent().size() == 0){
                break;
            }

            try {
                copyDealsToElasticSearch(dealsPage.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }


            pageNo++;
        }


    }

    @Transactional
    void copyDealsToElasticSearch(List<Deal> deals) throws IOException {
        // Set deal sync status

        List<Long> dealIds = deals.stream().map( deal -> deal.getId()).collect(Collectors.toList());

        dealRepository.updateDealsStatus(SyncStatus.IN_PROGRESS, dealIds);

        sendRecordsToElasticSearch( deals );

        dealRepository.updateDealsStatus(SyncStatus.SYNCED, dealIds);



    }

    private void sendRecordsToElasticSearch(List<Deal> deals) throws IOException {
        BulkRequest request = new BulkRequest();

        for ( Deal deal: deals) {
            request.add(new IndexRequest("leadlet", "deal")
                .source(XContentType.JSON, "id", deal.getId(),
                                            "created_date", new Date(deal.getCreatedDate().toEpochMilli()),
                                            "pipeline_id", deal.getPipeline().getId(),
                                            "stage_id", deal.getStage().getId()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);
    }


}
