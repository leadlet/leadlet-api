package com.leadlet.service.impl;

import com.google.common.collect.ImmutableMap;
import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.User;
import com.leadlet.domain.enumeration.FacetType;
import com.leadlet.repository.DealRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ElasticsearchServiceImpl implements ElasticsearchService {

    ImmutableMap<String, String> resourceIndexMap
        = ImmutableMap.of("activity", "leadlet-activity");

    private final RestHighLevelClient restHighLevelClient;
    private final DealRepository dealRepository;

    public ElasticsearchServiceImpl(RestHighLevelClient restHighLevelClient, DealRepository dealRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.dealRepository = dealRepository;
    }


    @Override
    public FacetDTO getFieldTerms(String id, String index, String fieldName, String query) throws IOException {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if(StringUtils.isEmpty(query)){
            query = appAccountFilter;
        }else {
            query += " AND " + appAccountFilter;
        }

        searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
        searchSourceBuilder.aggregation(AggregationBuilders
            .terms(id)
            .field(fieldName));

        searchRequest.source(searchSourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest);

        // sr is here your SearchResponse object
        Terms genders = sr.getAggregations().get(id);

        TermsFacetDTO facetDTO = new TermsFacetDTO();
        facetDTO.setId(id);

        for (Terms.Bucket entry : genders.getBuckets()) {
            facetDTO.addTerm(entry.getKeyAsString(),entry.getDocCount());
        }

        return facetDTO;

    }

    @Override
    public FacetDTO getFieldRange(String id, String index, String fieldName , String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String maxAggId = id + "-max";
        String minAggId = id + "-min";

        if( !StringUtils.isEmpty(query)){
            searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
        }

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

    @Override
    public Pair<List<Long>, Long> getEntityIds(String index, String query, Pageable pageable) throws IOException {
        SearchRequest searchRequest = buildSearchQuery(index, query, pageable);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        Pair<List<Long>, Long> response = parseDealIdsFromResponse(searchResponse, pageable);

        return response;
    }


    private Pair<List<Long>, Long> parseDealIdsFromResponse(SearchResponse searchResponse, Pageable pageable) {

        List<Long> ids = new ArrayList<>();

        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Long id = Long.valueOf( (Integer) sourceAsMap.get("id"));

            ids.add(id);
        }

        Pair<List<Long>, Long> response =  Pair.of(ids, searchHits.getTotalHits());

        return response;
    }

    private SearchRequest buildSearchQuery(String index, String query, Pageable pageable) {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[] {"id"};
        searchSourceBuilder.fetchSource(includeFields, null);
        searchSourceBuilder.from(pageable.getOffset());
        searchSourceBuilder.size(pageable.getPageSize());

        if( !StringUtils.isEmpty(query)){
            searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
        }

        if( pageable.getSort() != null){
            Iterator<Sort.Order> orderIterator = pageable.getSort().iterator();
            while(orderIterator.hasNext()){
                Sort.Order order = orderIterator.next();
                searchSourceBuilder = searchSourceBuilder.sort( order.getProperty()
                    , order.getDirection() == Sort.Direction.ASC ? SortOrder.ASC : SortOrder.DESC);
            }
        }

        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    public void indexDeal(Deal deal) throws IOException {

        IndexRequest request = new IndexRequest(SearchConstants.DEAL_INDEX, SearchConstants.DEAL_TYPE, String.valueOf(deal.getId()));

        DealSearchIndexDTO dealSearchIndexDTO = new DealSearchIndexDTO(deal);
        request.source(dealSearchIndexDTO.getBuilder());
        IndexResponse response = restHighLevelClient.index(request);
    }

    public void indexTimeline(Timeline timeline) throws IOException {


        IndexRequest request = new IndexRequest(SearchConstants.TIMELINE_INDEX, SearchConstants.TIMELINE_TYPE, String.valueOf(timeline.getId()));

        TimelineSearchIndexDTO timelineSearchIndexDTO = new TimelineSearchIndexDTO(timeline);
        request.source(timelineSearchIndexDTO.getBuilder());
        IndexResponse response = restHighLevelClient.index(request);
    }

    public void indexActivity(Activity activity) throws IOException {

        IndexRequest request = new IndexRequest(SearchConstants.ACTIVITY_INDEX, SearchConstants.ACTIVITY_TYPE, String.valueOf(activity.getId()));

        ActivitySearchIndexDTO activitySearchIndexDTO = new ActivitySearchIndexDTO(activity);
        request.source(activitySearchIndexDTO.getBuilder());
        IndexResponse response = restHighLevelClient.index(request);
    }

    @Override
    public Set<FacetDTO> getFilterData(String resource, Set<FilterDefinitionDTO> filterDefinitions, String query) throws IOException {

        String index = resourceIndexMap.get(resource);

        SearchSourceBuilder searchSourceBuilder = buildInitialSearchQuery(query);

        for (FilterDefinitionDTO filter:filterDefinitions) {
            addAggregation(searchSourceBuilder, filter);
        }

        SearchRequest searchRequest = new SearchRequest(index);

        searchRequest.source(searchSourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest);

        // sr is here your SearchResponse object

        Set<FacetDTO> filters = extractFiltersFromSearchResult(sr, filterDefinitions);

        return filters;
    }

    private Set<FacetDTO> extractFiltersFromSearchResult(SearchResponse sr, Set<FilterDefinitionDTO> filterDefinitions) {

        Set<FacetDTO> filters = new HashSet<>();

        for (FilterDefinitionDTO filter:filterDefinitions) {
            if(filter.getType().equals(FacetType.list)){
                Terms genders = sr.getAggregations().get(filter.getId());
                TermsFacetDTO facetDTO = new TermsFacetDTO();
                facetDTO.setId(filter.getId());
                facetDTO.setField(filter.getField());

                for (Terms.Bucket entry : genders.getBuckets()) {
                    facetDTO.addTerm(entry.getKeyAsString(),entry.getDocCount());
                }

                filters.add(facetDTO);
            }
        }
        return filters;
    }

    private void addAggregation(SearchSourceBuilder sourceBuilder, FilterDefinitionDTO filter) {
        if( filter.getType().equals(FacetType.list)){
            String fieldName = filter.getField();
            sourceBuilder.aggregation(AggregationBuilders
                .terms(filter.getId())
                .field(fieldName));
        }
    }

    private SearchSourceBuilder buildInitialSearchQuery(String query) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if(StringUtils.isEmpty(query)){
            query = appAccountFilter;
        }else {
            query += " AND " + appAccountFilter;
        }

        searchSourceBuilder = searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));

        return searchSourceBuilder;
    }

    public void indexUser(User user) throws IOException {

        IndexRequest request = new IndexRequest(SearchConstants.USER_INDEX, SearchConstants.USER_TYPE, String.valueOf(user.getId()));

        UserSearchIndexDTO userSearchIndexDTO = new UserSearchIndexDTO(user);
        request.source(userSearchIndexDTO.getBuilder());
        IndexResponse response = restHighLevelClient.index(request);
    }
}
