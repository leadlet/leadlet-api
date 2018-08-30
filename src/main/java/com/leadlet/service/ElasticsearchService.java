package com.leadlet.service;

import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import com.leadlet.service.dto.SearchQueryDTO;
import javafx.util.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ElasticsearchService {

    FacetDTO getFieldTerms(FacetDefinitionDTO facetDefinitions) throws IOException;

    Pair<List<Long>, Long> getDealsTerms(String query, Pageable pageable) throws IOException;

    void syncDeals();

}
