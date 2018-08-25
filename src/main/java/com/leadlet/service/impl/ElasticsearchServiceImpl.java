package com.leadlet.service.impl;

import com.leadlet.domain.enumeration.FacetType;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import com.leadlet.service.dto.SearchQueryDTO;
import com.leadlet.service.dto.TermsFacetDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class ElasticsearchServiceImpl implements ElasticsearchService {


    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchServiceImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
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
}
