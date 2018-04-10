package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dropbox.core.DbxException;
import com.leadlet.service.DocumentService;
import com.leadlet.service.dto.DocumentDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Document.
 */
@RestController
@RequestMapping("/api")
public class DocumentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentResource.class);

    private static final String ENTITY_NAME = "document";

    private final DocumentService documentService;

    public DocumentResource(DocumentService documentService) {
        this.documentService = documentService;
    }


    @PostMapping(value = "/documents", headers = "content-type=multipart/*")
    public ResponseEntity<DocumentDTO> upload(@RequestParam(value = "file", required = true) MultipartFile multipartFile, @RequestParam(value = "personId") long personId) throws IOException, DbxException {

        DocumentDTO documentDTO = documentService.save(multipartFile, personId);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentDTO.getId().toString()))
            .body(documentDTO);
    }

    @PostMapping(value = "/documentsOrg", headers = "content-type=multipart/*")
    public ResponseEntity<DocumentDTO> uploadForOrganization(@RequestParam(value = "file", required = true) MultipartFile multipartFile, @RequestParam(value = "organizationId") long organizationId) throws IOException, DbxException {

        DocumentDTO documentDTO = documentService.saveDocumentForOrganization(multipartFile, organizationId);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentDTO.getId().toString()))
            .body(documentDTO);
    }

    /**
     * PUT  /documents : Updates an existing document.
     *
     * @param documentDTO the documentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentDTO,
     * or with status 400 (Bad Request) if the documentDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/documents")
    @Timed
    public ResponseEntity<DocumentDTO> updateDocument(@RequestBody DocumentDTO documentDTO) throws URISyntaxException {
        log.debug("REST request to update Document : {}", documentDTO);

        DocumentDTO result = documentService.update(documentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /documents : get all the documents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documents in body
     */
    @GetMapping("/documents")
    @Timed
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        log.debug("REST request to get Documents");
        List<DocumentDTO> documentList = documentService.findAll();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentList));
    }

    @GetMapping("/documents/person/{personId}")
    @Timed
    public ResponseEntity<List<DocumentDTO>> getPersonDocuments(@PathVariable Long personId) {
        log.debug("REST request to get Documents for person {}", personId);

        List<DocumentDTO> documentList = documentService.findByPersonId(personId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentList));
    }

    @GetMapping("/documents/organization/{organizationId}")
    @Timed
    public ResponseEntity<List<DocumentDTO>> getOrganizationDocuments(@PathVariable Long organizationId) {
        log.debug("REST request to get Documents for organization {}", organizationId);

        List<DocumentDTO> documentList = documentService.findByOrganizationId(organizationId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentList));
    }

    @GetMapping("/documents/deal/{dealId}")
    @Timed
    public ResponseEntity<List<DocumentDTO>> getDealDocuments(@PathVariable Long dealId) {
        log.debug("REST request to get Documents for deal {}", dealId);

        List<DocumentDTO> documentList = documentService.findByDealId(dealId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentList));
    }

    /**
     * DELETE  /documents/:id : delete the "id" document.
     *
     * @param id the id of the documentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/documents/{id}")
    @Timed
    public ResponseEntity<DocumentDTO> deleteDocument(@PathVariable Long id) throws IOException {
        log.debug("REST request to delete Document : {}", id);
        documentService.delete(id);

        DocumentDTO result = new DocumentDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }

}
