package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.CompanySubscriptionPlanService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.CompanySubscriptionPlanDTO;
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
 * REST controller for managing CompanySubscriptionPlan.
 */
@RestController
@RequestMapping("/api")
public class CompanySubscriptionPlanResource {

    private final Logger log = LoggerFactory.getLogger(CompanySubscriptionPlanResource.class);

    private static final String ENTITY_NAME = "companySubscriptionPlan";

    private final CompanySubscriptionPlanService companySubscriptionPlanService;

    public CompanySubscriptionPlanResource(CompanySubscriptionPlanService companySubscriptionPlanService) {
        this.companySubscriptionPlanService = companySubscriptionPlanService;
    }

    /**
     * POST  /company-subscription-plans : Create a new companySubscriptionPlan.
     *
     * @param companySubscriptionPlanDTO the companySubscriptionPlanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companySubscriptionPlanDTO, or with status 400 (Bad Request) if the companySubscriptionPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-subscription-plans")
    @Timed
    public ResponseEntity<CompanySubscriptionPlanDTO> createCompanySubscriptionPlan(@RequestBody CompanySubscriptionPlanDTO companySubscriptionPlanDTO) throws URISyntaxException {
        log.debug("REST request to save CompanySubscriptionPlan : {}", companySubscriptionPlanDTO);
        if (companySubscriptionPlanDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companySubscriptionPlan cannot already have an ID")).body(null);
        }
        CompanySubscriptionPlanDTO result = companySubscriptionPlanService.save(companySubscriptionPlanDTO);
        return ResponseEntity.created(new URI("/api/company-subscription-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-subscription-plans : Updates an existing companySubscriptionPlan.
     *
     * @param companySubscriptionPlanDTO the companySubscriptionPlanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companySubscriptionPlanDTO,
     * or with status 400 (Bad Request) if the companySubscriptionPlanDTO is not valid,
     * or with status 500 (Internal Server Error) if the companySubscriptionPlanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-subscription-plans")
    @Timed
    public ResponseEntity<CompanySubscriptionPlanDTO> updateCompanySubscriptionPlan(@RequestBody CompanySubscriptionPlanDTO companySubscriptionPlanDTO) throws URISyntaxException {
        log.debug("REST request to update CompanySubscriptionPlan : {}", companySubscriptionPlanDTO);
        if (companySubscriptionPlanDTO.getId() == null) {
            return createCompanySubscriptionPlan(companySubscriptionPlanDTO);
        }
        CompanySubscriptionPlanDTO result = companySubscriptionPlanService.save(companySubscriptionPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companySubscriptionPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-subscription-plans : get all the companySubscriptionPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companySubscriptionPlans in body
     */
    @GetMapping("/company-subscription-plans")
    @Timed
    public ResponseEntity<List<CompanySubscriptionPlanDTO>> getAllCompanySubscriptionPlans(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanySubscriptionPlans");
        Page<CompanySubscriptionPlanDTO> page = companySubscriptionPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-subscription-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-subscription-plans/:id : get the "id" companySubscriptionPlan.
     *
     * @param id the id of the companySubscriptionPlanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companySubscriptionPlanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-subscription-plans/{id}")
    @Timed
    public ResponseEntity<CompanySubscriptionPlanDTO> getCompanySubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to get CompanySubscriptionPlan : {}", id);
        CompanySubscriptionPlanDTO companySubscriptionPlanDTO = companySubscriptionPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companySubscriptionPlanDTO));
    }

    /**
     * DELETE  /company-subscription-plans/:id : delete the "id" companySubscriptionPlan.
     *
     * @param id the id of the companySubscriptionPlanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-subscription-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanySubscriptionPlan(@PathVariable Long id) {
        log.debug("REST request to delete CompanySubscriptionPlan : {}", id);
        companySubscriptionPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
