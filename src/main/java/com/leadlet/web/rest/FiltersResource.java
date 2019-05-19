package com.leadlet.web.rest;

import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.FacetDTO;
import com.leadlet.service.dto.FilterDefinitionDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class FiltersResource {

    private final Logger log = LoggerFactory.getLogger(FiltersResource.class);

    private static final String ENTITY_NAME = "filters";

    private final ElasticsearchService filterService;

    public FiltersResource(ElasticsearchService filterService) {
        this.filterService = filterService;
    }

    @PostMapping("/filters/{resource}")
    public ResponseEntity<Set<FacetDTO>> buildFilterData(@RequestBody Set<FilterDefinitionDTO> filterDefinitions,
                                                         @PathVariable String resource,
                                                         @ApiParam String q) throws IOException {
        log.debug(Arrays.toString(filterDefinitions.toArray()));

        Set<FacetDTO> filters = filterService.getFilterData(resource, filterDefinitions, q);
        return new ResponseEntity<>(filters, HttpStatus.OK);
    }

}
