package com.leadlet.web.rest;

import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/facets/terms/{id}/{index}")
    public ResponseEntity<FacetDTO> getDistinctTerms(@PathVariable String id, @PathVariable String index, @ApiParam String field, @ApiParam String q) throws IOException {
        FacetDTO facet = filterService.getFieldTerms(id, index, field ,q);

        return new ResponseEntity<>(facet, HttpStatus.OK);
    }

    @GetMapping("/facets/range/{id}/{index}")
    public ResponseEntity<FacetDTO> getFieldRange(@PathVariable String id, @PathVariable String index, @ApiParam String field, @ApiParam String q) throws IOException {
        FacetDTO facet = filterService.getFieldRange(id, index, field ,q);

        return new ResponseEntity<>(facet, HttpStatus.OK);
    }

}
