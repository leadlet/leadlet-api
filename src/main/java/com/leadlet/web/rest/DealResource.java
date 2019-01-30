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
     * @param dealDTO the detailedDealDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailedDealDTO, or with status 400 (Bad Request) if the deal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deals")
    @Timed
    public ResponseEntity<DetailedDealDTO> createDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Deal : {}", dealDTO);
        if (dealDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deal cannot already have an ID")).body(null);
        }
        Character c = null;

        DetailedDealDTO result = dealService.save(dealDTO);
        // TODO lazy load workaround. above save method does not return stage.pipeline
        result = dealService.findOne(result.getId());
        return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/deals")
    @Timed
    public ResponseEntity<List<DetailedDealDTO>> getDeals(@ApiParam String q, @ApiParam Pageable pageable) throws URISyntaxException, IOException {

        Page<DetailedDealDTO> page = dealService.query(q, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deals/search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }


    @PutMapping("/deals/{id}/stage/{stageId}")
    @Timed
    public ResponseEntity<DetailedDealDTO> updateDealStage(@PathVariable Long id, @PathVariable Long stageId ) throws IOException {

        DetailedDealDTO result = dealService.updateStage(id, stageId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }

    /**
     * PUT  /deals : Updates an existing deal.
     *
     * @param dealDTO the detailedDealDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailedDealDTO,
     * or with status 400 (Bad Request) if the detailedDealDTO is not valid,
     * or with status 500 (Internal Server Error) if the detailedDealDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deals")
    @Timed
    public ResponseEntity<DetailedDealDTO> updateDeal(@RequestBody DealDTO dealDTO, @RequestHeader("modified-fields") List<String> modifiedFields) throws URISyntaxException, IOException {
        log.debug("REST request to update Deal : {}", dealDTO);

        DetailedDealDTO result = dealService.update(dealDTO, modifiedFields);
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
    public ResponseEntity<DetailedDealDTO> getDeal(@PathVariable Long id) {
        log.debug("REST request to get Deal : {}", id);
        DetailedDealDTO detailedDealDTO = dealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailedDealDTO));
    }

    /**
     * DELETE  /deals/:id : delete the "id" deal.
     *
     * @param id the id of the dealDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deals/{id}")
    @Timed
    public ResponseEntity<DetailedDealDTO> deleteDeal(@PathVariable Long id) {
        log.debug("REST request to delete Deal : {}", id);
        dealService.delete(id);

        DetailedDealDTO result = new DetailedDealDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }
}
