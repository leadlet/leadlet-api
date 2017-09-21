package com.leadlet.service;

import com.leadlet.service.dto.EmailTemplatesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmailTemplates.
 */
public interface EmailTemplatesService {

    /**
     * Save a emailTemplates.
     *
     * @param emailTemplatesDTO the entity to save
     * @return the persisted entity
     */
    EmailTemplatesDTO save(EmailTemplatesDTO emailTemplatesDTO);

    /**
     *  Get all the emailTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EmailTemplatesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" emailTemplates.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmailTemplatesDTO findOne(Long id);

    /**
     *  Delete the "id" emailTemplates.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
