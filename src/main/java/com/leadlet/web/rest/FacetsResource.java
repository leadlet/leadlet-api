package com.leadlet.web.rest;

import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FacetDefinitionDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class FacetsResource {

    private final Logger log = LoggerFactory.getLogger(FacetsResource.class);

    private static final String ENTITY_NAME = "filters";

    private final ElasticsearchService filterService;

    public FacetsResource(ElasticsearchService filterService) {
        this.filterService = filterService;
    }

    /**
     * GET  /aggregations : get all the stages.
     *
     * @param facetDefinition the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @PostMapping("/facets")
    public ResponseEntity<FacetDTO> getDistinctTerms(@RequestBody FacetDefinitionDTO facetDefinition, @ApiParam String q) throws IOException {
        FacetDTO facet = filterService.getFieldTerms(facetDefinition,q);

        return new ResponseEntity<>(facet, HttpStatus.OK);
    }

    @GetMapping("/facets/range/{id}/{fieldName}")
    public ResponseEntity<FacetDTO> getFieldRange(@PathVariable String id, @PathVariable String fieldName) throws IOException {
        FacetDTO facet = filterService.getFieldRange(id, fieldName);

        return new ResponseEntity<>(facet, HttpStatus.OK);
    }

}
