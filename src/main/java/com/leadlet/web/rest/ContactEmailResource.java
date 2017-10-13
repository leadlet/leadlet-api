package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.ContactEmailService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.ContactEmailDTO;
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
 * REST controller for managing ContactEmail.
 */
@RestController
@RequestMapping("/api")
public class ContactEmailResource {

    private final Logger log = LoggerFactory.getLogger(ContactEmailResource.class);

    private static final String ENTITY_NAME = "contactEmail";

    private final ContactEmailService contactEmailService;

    public ContactEmailResource(ContactEmailService contactEmailService) {
        this.contactEmailService = contactEmailService;
    }

    /**
     * POST  /contact-emails : Create a new contactEmail.
     *
     * @param contactEmailDTO the contactEmailDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactEmailDTO, or with status 400 (Bad Request) if the contactEmail has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-emails")
    @Timed
    public ResponseEntity<ContactEmailDTO> createContactEmail(@RequestBody ContactEmailDTO contactEmailDTO) throws URISyntaxException {
        log.debug("REST request to save ContactEmail : {}", contactEmailDTO);
        if (contactEmailDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contactEmail cannot already have an ID")).body(null);
        }
        ContactEmailDTO result = contactEmailService.save(contactEmailDTO);
        return ResponseEntity.created(new URI("/api/contact-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-emails : Updates an existing contactEmail.
     *
     * @param contactEmailDTO the contactEmailDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactEmailDTO,
     * or with status 400 (Bad Request) if the contactEmailDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactEmailDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-emails")
    @Timed
    public ResponseEntity<ContactEmailDTO> updateContactEmail(@RequestBody ContactEmailDTO contactEmailDTO) throws URISyntaxException {
        log.debug("REST request to update ContactEmail : {}", contactEmailDTO);

        ContactEmailDTO result = contactEmailService.update(contactEmailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactEmailDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-emails : get all the contactEmails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactEmails in body
     */
    @GetMapping("/contact-emails")
    @Timed
    public ResponseEntity<List<ContactEmailDTO>> getAllContactEmails(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ContactEmails");
        Page<ContactEmailDTO> page = contactEmailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-emails");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contact-emails/:id : get the "id" contactEmail.
     *
     * @param id the id of the contactEmailDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactEmailDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-emails/{id}")
    @Timed
    public ResponseEntity<ContactEmailDTO> getContactEmail(@PathVariable Long id) {
        log.debug("REST request to get ContactEmail : {}", id);
        ContactEmailDTO contactEmailDTO = contactEmailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactEmailDTO));
    }

    /**
     * DELETE  /contact-emails/:id : delete the "id" contactEmail.
     *
     * @param id the id of the contactEmailDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-emails/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactEmail(@PathVariable Long id) {
        log.debug("REST request to delete ContactEmail : {}", id);
        contactEmailService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
