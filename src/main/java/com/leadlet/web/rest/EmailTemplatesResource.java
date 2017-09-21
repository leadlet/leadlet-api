package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.EmailTemplatesService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.EmailTemplatesDTO;
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
 * REST controller for managing EmailTemplates.
 */
@RestController
@RequestMapping("/api")
public class EmailTemplatesResource {

    private final Logger log = LoggerFactory.getLogger(EmailTemplatesResource.class);

    private static final String ENTITY_NAME = "emailTemplates";

    private final EmailTemplatesService emailTemplatesService;

    public EmailTemplatesResource(EmailTemplatesService emailTemplatesService) {
        this.emailTemplatesService = emailTemplatesService;
    }

    /**
     * POST  /email-templates : Create a new emailTemplates.
     *
     * @param emailTemplatesDTO the emailTemplatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailTemplatesDTO, or with status 400 (Bad Request) if the emailTemplates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplatesDTO> createEmailTemplates(@RequestBody EmailTemplatesDTO emailTemplatesDTO) throws URISyntaxException {
        log.debug("REST request to save EmailTemplates : {}", emailTemplatesDTO);
        if (emailTemplatesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new emailTemplates cannot already have an ID")).body(null);
        }
        EmailTemplatesDTO result = emailTemplatesService.save(emailTemplatesDTO);
        return ResponseEntity.created(new URI("/api/email-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-templates : Updates an existing emailTemplates.
     *
     * @param emailTemplatesDTO the emailTemplatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailTemplatesDTO,
     * or with status 400 (Bad Request) if the emailTemplatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailTemplatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplatesDTO> updateEmailTemplates(@RequestBody EmailTemplatesDTO emailTemplatesDTO) throws URISyntaxException {
        log.debug("REST request to update EmailTemplates : {}", emailTemplatesDTO);
        if (emailTemplatesDTO.getId() == null) {
            return createEmailTemplates(emailTemplatesDTO);
        }
        EmailTemplatesDTO result = emailTemplatesService.save(emailTemplatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailTemplatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-templates : get all the emailTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emailTemplates in body
     */
    @GetMapping("/email-templates")
    @Timed
    public ResponseEntity<List<EmailTemplatesDTO>> getAllEmailTemplates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EmailTemplates");
        Page<EmailTemplatesDTO> page = emailTemplatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /email-templates/:id : get the "id" emailTemplates.
     *
     * @param id the id of the emailTemplatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailTemplatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<EmailTemplatesDTO> getEmailTemplates(@PathVariable Long id) {
        log.debug("REST request to get EmailTemplates : {}", id);
        EmailTemplatesDTO emailTemplatesDTO = emailTemplatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emailTemplatesDTO));
    }

    /**
     * DELETE  /email-templates/:id : delete the "id" emailTemplates.
     *
     * @param id the id of the emailTemplatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailTemplates(@PathVariable Long id) {
        log.debug("REST request to delete EmailTemplates : {}", id);
        emailTemplatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
