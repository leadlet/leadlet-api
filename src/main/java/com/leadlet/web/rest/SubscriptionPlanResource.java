package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.SubscriptionPlanService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.SubscriptionPlanDTO;
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
 * REST controller for managing SubscriptionPlan.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionPlanResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionPlanResource.class);

    private static final String ENTITY_NAME = "subscriptionPlan";

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanResource(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    /**
     * POST  /subscription-plans : Create a new subscriptionPlan.
     *
     * @param subscriptionPlanDTO the subscriptionPlanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriptionPlanDTO, or with status 400 (Bad Request) if the subscriptionPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscription-plans")
    @Timed
    public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(@RequestBody SubscriptionPlanDTO subscriptionPlanDTO) throws URISyntaxException {
        log.debug("REST request to save SubscriptionPlan : {}", subscriptionPlanDTO);
        if (subscriptionPlanDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subscriptionPlan cannot already have an ID")).body(null);
        }
        SubscriptionPlanDTO result = subscriptionPlanService.save(subscriptionPlanDTO);
        return ResponseEntity.created(new URI("/api/subscription-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscription-plans : Updates an existing subscriptionPlan.
     *
     * @param subscriptionPlanDTO the subscriptionPlanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriptionPlanDTO,
     * or with status 400 (Bad Request) if the subscriptionPlanDTO is not valid,
     * or with status 500 (Internal Server Error) if the subscriptionPlanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscription-plans")
    @Timed
    public ResponseEntity<SubscriptionPlanDTO> updateSubscriptionPlan(@RequestBody SubscriptionPlanDTO subscriptionPlanDTO) throws URISyntaxException {
        log.debug("REST request to update SubscriptionPlan : {}", subscriptionPlanDTO);
        if (subscriptionPlanDTO.getId() == null) {
            return createSubscriptionPlan(subscriptionPlanDTO);
        }
        SubscriptionPlanDTO result = subscriptionPlanService.save(subscriptionPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscriptionPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscription-plans : get all the subscriptionPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subscriptionPlans in body
     */
    @GetMapping("/subscription-plans")
    @Timed
    public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SubscriptionPlans");
        Page<SubscriptionPlanDTO> page = subscriptionPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subscription-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subscription-plans/:id : get the "id" subscriptionPlan.
     *
     * @param id the id of the subscriptionPlanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriptionPlanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/subscription-plans/{id}")
    @Timed
    public ResponseEntity<SubscriptionPlanDTO> getSubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to get SubscriptionPlan : {}", id);
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriptionPlanDTO));
    }

    /**
     * DELETE  /subscription-plans/:id : delete the "id" subscriptionPlan.
     *
     * @param id the id of the subscriptionPlanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subscription-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to delete SubscriptionPlan : {}", id);
        subscriptionPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
