package com.leadlet.service.impl;

import com.leadlet.domain.Deal;
import com.leadlet.domain.Product;
import com.leadlet.domain.enumeration.FacetType;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import com.leadlet.service.dto.RangeFacetDTO;
import com.leadlet.service.dto.TermsFacetDTO;
import javafx.util.Pair;
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
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public FacetDTO getFieldTerms(FacetDefinitionDTO facetDefinition, String query) throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if( !StringUtils.isEmpty(query)){
            searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
        }
        searchSourceBuilder.aggregation(AggregationBuilders
            .terms(facetDefinition.getId())
            .field(facetDefinition.getDataField()));

        searchRequest.source(searchSourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest);

        // sr is here your SearchResponse object
        Terms genders = sr.getAggregations().get(facetDefinition.getId());

        TermsFacetDTO facetDTO = new TermsFacetDTO();
        facetDTO.setId(facetDefinition.getId());

        for (Terms.Bucket entry : genders.getBuckets()) {
            facetDTO.addTerm(entry.getKey().toString(),entry.getDocCount());
        }

        return facetDTO;

    }

    @Override
    public Pair<List<Long>, Long> getDealsTerms(String query, Pageable pageable) throws IOException {
        SearchRequest searchRequest = buildSearchQuery(query, pageable);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);

        Pair<List<Long>, Long> response = getDealIds(searchResponse, pageable);

        return response;
    }


    private Pair<List<Long>, Long> getDealIds(SearchResponse searchResponse, Pageable pageable) {

        List<Long> ids = new ArrayList<>();

        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Long id = Long.valueOf( (Integer) sourceAsMap.get("id"));

            ids.add(id);
        }

        Pair<List<Long>, Long> response = new Pair<>(ids, searchHits.getTotalHits());

        return response;
    }

    private SearchRequest buildSearchQuery(String query, Pageable pageable) {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[] {"id"};
        searchSourceBuilder.fetchSource(includeFields, null);
        searchSourceBuilder.from(pageable.getOffset());
        searchSourceBuilder.size(pageable.getPageSize());

        searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));

        searchRequest.source(searchSourceBuilder);
        return searchRequest;
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

    @Override
    public FacetDTO getFieldRange(String id, String fieldName) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String maxAggId = id + "-max";
        String minAggId = id + "-min";

        searchSourceBuilder.aggregation(AggregationBuilders
            .max(maxAggId)
            .field(fieldName))
        .aggregation(AggregationBuilders
            .min(minAggId)
            .field(fieldName));

        searchRequest.source(searchSourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest);

        // sr is here your SearchResponse object
        Max maxAgg = sr.getAggregations().get(maxAggId);
        Min minAgg = sr.getAggregations().get(minAggId);

        RangeFacetDTO facetDTO = new RangeFacetDTO();
        facetDTO.setId(id);
        facetDTO.setMax(maxAgg.getValue());
        facetDTO.setMin(minAgg.getValue());

        return facetDTO;
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
                                            "stage_id", deal.getStage().getId(),
                                            "priority", deal.getPriority(),
                                            "source", !StringUtils.isEmpty(deal.getDealSource()) ? deal.getDealSource().getName() : "",
                                            "channel", !StringUtils.isEmpty(deal.getDealChannel()) ? deal.getDealChannel().getName() : "",
                                            "products", deal.getProducts().stream().map(Product::getDescription).toArray()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);
    }


}
