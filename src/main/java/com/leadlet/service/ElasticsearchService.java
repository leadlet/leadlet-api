package com.leadlet.service;

import com.leadlet.domain.Deal;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchService {

    FacetDTO getFieldTerms(FacetDefinitionDTO facetDefinitions, String query) throws IOException;

    Pair<List<Long>, Long> getDealsTerms(String query, Pageable pageable) throws IOException;

    FacetDTO getFieldRange(String id, String fieldName) throws IOException;

    void indexDeal(Deal deal) throws IOException ;
}
