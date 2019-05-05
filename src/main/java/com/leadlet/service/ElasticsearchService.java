package com.leadlet.service;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FilterDefinitionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ElasticsearchService {

    FacetDTO getFieldTerms(String id, String index, String fieldName , String q) throws IOException;
    FacetDTO getFieldRange(String id, String index, String fieldName , String q) throws IOException;

    Pair<List<Long>, Long> getEntityIds(String index, String query, Pageable pageable)  throws IOException;

    void indexDeal(Deal deal) throws IOException ;
    void indexTimeline(Timeline timeline) throws IOException ;
    void indexActivity(Activity activity) throws IOException ;

    Set<FacetDTO> getFilterData(String resource, Set<FilterDefinitionDTO> filterDefinitions, String query) throws IOException;
}
