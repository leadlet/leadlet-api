package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.OrganizationService;
import com.leadlet.service.dto.OrganizationDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    private static final String ENTITY_NAME = "organization";

    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * POST  /organizations : Create a new organization.
     *
     * @param organizationDTO the organizationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationDTO, or with status 400 (Bad Request) if the organization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizations")
    @Timed
    public ResponseEntity<OrganizationDTO> createOrganization(@Validated @RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organizationDTO);
        if (organizationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organization cannot already have an ID")).body(null);
        }
        OrganizationDTO result = organizationService.save(organizationDTO);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organization.
     *
     * @param organizationDTO the organizationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationDTO,
     * or with status 400 (Bad Request) if the organizationDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizations")
    @Timed
    public ResponseEntity<OrganizationDTO> updateOrganization(@RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organizationDTO);

        OrganizationDTO result = organizationService.update(organizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations")
    @Timed
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(@ApiParam String filter, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Organizations");

        Page<OrganizationDTO> page = organizationService.search(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param id the id of the organizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        OrganizationDTO organizationDTO = organizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organizationDTO));
    }

    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param personId the id of the organizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/person/{personId}")
    @Timed
    public ResponseEntity<List<OrganizationDTO>> getOrganizationByPersonId(@PathVariable Long personId) {
        log.debug("REST request to get Organization : {}", personId);
        OrganizationDTO organizationDTO = organizationService.findOneByPersonId(personId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(Arrays.asList(organizationDTO)));
    }

    /**
     * DELETE  /persons/:idList : delete the "idList" person.
     *
     * @param idList the id of the personDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizations/{idList}")
    @Timed
    public ResponseEntity<List<Long>> deleteOrganization(@PathVariable List<Long> idList) {
        log.debug("REST request to delete Person : {}", idList);
        organizationService.delete(idList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME,""))
            .body(idList);
    }
}
