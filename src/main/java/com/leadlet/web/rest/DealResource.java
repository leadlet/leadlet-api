package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.DealService;
import com.leadlet.service.dto.*;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Deal.
 */
@RestController
@RequestMapping("/api")
public class DealResource {

    private final Logger log = LoggerFactory.getLogger(DealResource.class);

    private static final String ENTITY_NAME = "deal";

    private final DealService dealService;

    public DealResource(DealService dealService) {
        this.dealService = dealService;
    }

    /**
     * POST  /deals : Create a new deal.
     *
     * @param dealDTO the dealDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealDTO, or with status 400 (Bad Request) if the deal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deals")
    @Timed
    public ResponseEntity<DealDTO> createDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Deal : {}", dealDTO);
        if (dealDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deal cannot already have an ID")).body(null);
        }
        Character c = null;

        DealDTO result = dealService.save(dealDTO);
        // TODO lazy load workaround. above save method does not return stage.pipeline
        result = dealService.findOne(result.getId());
        return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/deals")
    @Timed
    public ResponseEntity<List<DealDTO>> search(@ApiParam String q, @ApiParam Pageable pageable) throws URISyntaxException, IOException {

        Page<DealDTO> page = dealService.query(q, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deals/search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }


    @PutMapping("/deals/{id}/partial")
    @Timed
    public ResponseEntity<DealDTO> patchDeal(@PathVariable Long id, @ApiParam Integer priority,  @ApiParam Long stageId ) throws URISyntaxException, IOException {

        DealDTO result = dealService.patch(id, priority, stageId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }

    /**
     * PUT  /deals : Updates an existing deal.
     *
     * @param dealDTO the dealDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealDTO,
     * or with status 400 (Bad Request) if the dealDTO is not valid,
     * or with status 500 (Internal Server Error) if the dealDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deals")
    @Timed
    public ResponseEntity<DealDTO> updateDeal(@RequestBody DealDTO dealDTO, @RequestHeader("modified-fields") List<String> modifiedFields) throws URISyntaxException, IOException {
        log.debug("REST request to update Deal : {}", dealDTO);

        DealDTO result = dealService.update(dealDTO, modifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealDTO.getId().toString()))
            .body(result);
    }


    /**
     * GET  /deals/:id : get the "id" deal.
     *
     * @param id the id of the dealDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deals/{id}")
    @Timed
    public ResponseEntity<DealDTO> getDeal(@PathVariable Long id) {
        log.debug("REST request to get Deal : {}", id);
        DealDTO dealDTO = dealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealDTO));
    }

    /**
     * DELETE  /deals/:id : delete the "id" deal.
     *
     * @param id the id of the dealDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deals/{id}")
    @Timed
    public ResponseEntity<DealDTO> deleteDeal(@PathVariable Long id) {
        log.debug("REST request to delete Deal : {}", id);
        dealService.delete(id);

        DealDTO result = new DealDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }
}
