package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.DealService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.dto.DealMoveDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.DealDTO;
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
    public ResponseEntity<DealDTO> createDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException {
        log.debug("REST request to save Deal : {}", dealDTO);
        if (dealDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deal cannot already have an ID")).body(null);
        }
        DealDTO result = dealService.save(dealDTO);
        return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /deals : Create a new deal.
     *
     * @param dealMoveDTO the dealDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealDTO, or with status 400 (Bad Request) if the deal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deals/move")
    @Timed
    public ResponseEntity<DealDTO> moveDeal(@RequestBody DealMoveDTO dealMoveDTO) throws URISyntaxException {
        log.debug("REST request to move Deal : {}", dealMoveDTO);

        dealService.move(dealMoveDTO);
        DealDTO result = dealService.findOne(dealMoveDTO.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealMoveDTO.getId().toString()))
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
    public ResponseEntity<DealDTO> updateDeal(@RequestBody DealDTO dealDTO) throws URISyntaxException {
        log.debug("REST request to update Deal : {}", dealDTO);

        DealDTO result = dealService.update(dealDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deals : get all the deals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deals in body
     */
    @GetMapping("/deals")
    @Timed
    public ResponseEntity<List<DealDTO>> getAllDeals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Deals");
        Page<DealDTO> page = dealService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
