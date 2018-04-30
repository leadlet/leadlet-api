package com.leadlet.service;

import com.leadlet.service.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DocumentService {

    /**
     * Save a document.
     *
     * @param multipartFile the entity to save
     * @return the persisted entity
     */
    DocumentDTO save(MultipartFile multipartFile, long personId) throws IOException,  SQLException;

    /**
     * Save a document.
     *
     * @param multipartFile the entity to save
     * @return the persisted entity
     */
    DocumentDTO saveDocumentForOrganization(MultipartFile multipartFile, long organizationId) throws IOException,  SQLException;

    /**
     * Update a document.
     *
     * @param documentDTO the entity to update
     * @return the persisted entity
     */
    DocumentDTO update(DocumentDTO documentDTO);

    /**
     * Get all the documents.
     *
     * @return the list of entities
     */
    List<DocumentDTO> findAll();


    /**
     * Get all the documents.
     *
     * @return the list of entities
     */
    List<DocumentDTO> findByPersonId(Long personId);

    /**
     * Get all the documents.
     *
     * @return the list of entities
     */
    List<DocumentDTO> findByOrganizationId(Long organizationId);

    /**
     * Get all the documents.
     *
     * @return the list of entities
     */
    List<DocumentDTO> findByDealId(Long dealId);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws IOException, SQLException;

}
