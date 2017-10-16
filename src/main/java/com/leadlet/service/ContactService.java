package com.leadlet.service;

import com.leadlet.domain.enumeration.ContactType;
import com.leadlet.service.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Contact.
 */
public interface ContactService {

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    ContactDTO save(ContactDTO contactDTO);

    /**
     * Update a contact.
     *
     * @param contactDTO the entity to update
     * @return the persisted entity
     */
    ContactDTO update(ContactDTO contactDTO);

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactDTO> findAll(Pageable pageable);

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactDTO> findByType(ContactType type, Pageable pageable);

    /**
     *  Get the "id" contact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactDTO findOne(Long id);

    /**
     *  Delete the "id" contact.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
