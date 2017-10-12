package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.ContactPhoneService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.ContactPhoneDTO;
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
 * REST controller for managing ContactPhone.
 */
@RestController
@RequestMapping("/api")
public class ContactPhoneResource {

    private final Logger log = LoggerFactory.getLogger(ContactPhoneResource.class);

    private static final String ENTITY_NAME = "contactPhone";

    private final ContactPhoneService contactPhoneService;

    public ContactPhoneResource(ContactPhoneService contactPhoneService) {
        this.contactPhoneService = contactPhoneService;
    }

    /**
     * POST  /contact-phones : Create a new contactPhone.
     *
     * @param contactPhoneDTO the contactPhoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactPhoneDTO, or with status 400 (Bad Request) if the contactPhone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-phones")
    @Timed
    public ResponseEntity<ContactPhoneDTO> createContactPhone(@RequestBody ContactPhoneDTO contactPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save ContactPhone : {}", contactPhoneDTO);
        if (contactPhoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contactPhone cannot already have an ID")).body(null);
        }
        ContactPhoneDTO result = contactPhoneService.save(contactPhoneDTO);
        return ResponseEntity.created(new URI("/api/contact-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-phones : Updates an existing contactPhone.
     *
     * @param contactPhoneDTO the contactPhoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactPhoneDTO,
     * or with status 400 (Bad Request) if the contactPhoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactPhoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-phones")
    @Timed
    public ResponseEntity<ContactPhoneDTO> updateContactPhone(@RequestBody ContactPhoneDTO contactPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update ContactPhone : {}", contactPhoneDTO);

        ContactPhoneDTO result = contactPhoneService.update(contactPhoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-phones : get all the contactPhones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactPhones in body
     */
    @GetMapping("/contact-phones")
    @Timed
    public ResponseEntity<List<ContactPhoneDTO>> getAllContactPhones(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ContactPhones");
        Page<ContactPhoneDTO> page = contactPhoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-phones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contact-phones/:id : get the "id" contactPhone.
     *
     * @param id the id of the contactPhoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactPhoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contact-phones/{id}")
    @Timed
    public ResponseEntity<ContactPhoneDTO> getContactPhone(@PathVariable Long id) {
        log.debug("REST request to get ContactPhone : {}", id);
        ContactPhoneDTO contactPhoneDTO = contactPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactPhoneDTO));
    }

    /**
     * DELETE  /contact-phones/:id : delete the "id" contactPhone.
     *
     * @param id the id of the contactPhoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-phones/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactPhone(@PathVariable Long id) {
        log.debug("REST request to delete ContactPhone : {}", id);
        contactPhoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
