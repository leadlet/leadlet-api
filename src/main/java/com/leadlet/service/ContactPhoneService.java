package com.leadlet.service;

import com.leadlet.service.dto.ContactPhoneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContactPhone.
 */
public interface ContactPhoneService {

    /**
     * Save a contactPhone.
     *
     * @param contactPhoneDTO the entity to save
     * @return the persisted entity
     */
    ContactPhoneDTO save(ContactPhoneDTO contactPhoneDTO);

    /**
     *  Get all the contactPhones.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactPhoneDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" contactPhone.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactPhoneDTO findOne(Long id);

    /**
     *  Delete the "id" contactPhone.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
