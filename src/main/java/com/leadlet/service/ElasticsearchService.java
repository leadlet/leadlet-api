package com.leadlet.service;

import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import com.leadlet.service.dto.SearchQueryDTO;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ElasticsearchService {

    FacetDTO getFieldTerms(FacetDefinitionDTO facetDefinitions) throws IOException;

    List<Long> getDealsTerms(String query) throws IOException;

}
