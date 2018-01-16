package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.OrganizationPhoneService;
import com.leadlet.service.dto.OrganizationPhoneDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing OrganizationPhone.
 */
@RestController
@RequestMapping("/api")
public class OrganizationPhoneResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationPhoneResource.class);

    private static final String ENTITY_NAME = "organizationPhone";

    private final OrganizationPhoneService organizationPhoneService;

    public OrganizationPhoneResource(OrganizationPhoneService organizationPhoneService) {
        this.organizationPhoneService = organizationPhoneService;
    }

    /**
     * POST  /organization-phones : Create a new organizationPhone.
     *
     * @param organizationPhoneDTO the organizationPhoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationPhoneDTO, or with status 400 (Bad Request) if the organizationPhone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organization-phones")
    @Timed
    public ResponseEntity<OrganizationPhoneDTO> createOrganizationPhone(@RequestBody OrganizationPhoneDTO organizationPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save OrganizationPhone : {}", organizationPhoneDTO);
        if (organizationPhoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organizationPhone cannot already have an ID")).body(null);
        }
        OrganizationPhoneDTO result = organizationPhoneService.save(organizationPhoneDTO);
        return ResponseEntity.created(new URI("/api/organization-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organization-phones : Updates an existing organizationPhone.
     *
     * @param organizationPhoneDTO the organizationPhoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationPhoneDTO,
     * or with status 400 (Bad Request) if the organizationPhoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizationPhoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organization-phones")
    @Timed
    public ResponseEntity<OrganizationPhoneDTO> updateOrganizationPhone(@RequestBody OrganizationPhoneDTO organizationPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update OrganizationPhone : {}", organizationPhoneDTO);

        OrganizationPhoneDTO result = organizationPhoneService.update(organizationPhoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organization-phones : get all the organizationPhones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizationPhones in body
     */
    @GetMapping("/organization-phones")
    @Timed
    public ResponseEntity<List<OrganizationPhoneDTO>> getAllOrganizationPhones(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OrganizationPhones");
        Page<OrganizationPhoneDTO> page = organizationPhoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organization-phones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organization-phones/:id : get the "id" organizationPhone.
     *
     * @param id the id of the organizationPhoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationPhoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organization-phones/{id}")
    @Timed
    public ResponseEntity<OrganizationPhoneDTO> getOrganizationPhone(@PathVariable Long id) {
        log.debug("REST request to get OrganizationPhone : {}", id);
        OrganizationPhoneDTO organizationPhoneDTO = organizationPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organizationPhoneDTO));
    }

    /**
     * DELETE  /organization-phones/:id : delete the "id" organizationPhone.
     *
     * @param id the id of the organizationPhoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organization-phones/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganizationPhone(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationPhone : {}", id);
        organizationPhoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
