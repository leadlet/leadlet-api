package com.leadlet.service;

import com.leadlet.service.dto.OrganizationPhoneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OrganizationPhone.
 */
public interface OrganizationPhoneService {

    /**
     * Save a OrganizationPhone.
     *
     * @param OrganizationPhoneDTO the entity to save
     * @return the persisted entity
     */
    OrganizationPhoneDTO save(OrganizationPhoneDTO OrganizationPhoneDTO);

    /**
     * Update a OrganizationPhone.
     *
     * @param OrganizationPhoneDTO the entity to update
     * @return the persisted entity
     */
    OrganizationPhoneDTO update(OrganizationPhoneDTO OrganizationPhoneDTO);

    /**
     *  Get all the OrganizationPhones.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrganizationPhoneDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" OrganizationPhone.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrganizationPhoneDTO findOne(Long id);

    /**
     *  Delete the "id" OrganizationPhone.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
