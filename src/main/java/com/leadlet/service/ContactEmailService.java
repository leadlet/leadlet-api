package com.leadlet.service;

import com.leadlet.service.dto.ContactEmailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContactEmail.
 */
public interface ContactEmailService {

    /**
     * Save a contactEmail.
     *
     * @param contactEmailDTO the entity to save
     * @return the persisted entity
     */
    ContactEmailDTO save(ContactEmailDTO contactEmailDTO);

    /**
     *  Get all the contactEmails.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactEmailDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" contactEmail.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactEmailDTO findOne(Long id);

    /**
     *  Delete the "id" contactEmail.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
