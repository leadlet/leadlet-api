package com.leadlet.service;

import com.leadlet.service.dto.OrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Organization.
 */
public interface OrganizationService {

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    OrganizationDTO save(OrganizationDTO organizationDTO);

    /**
     * Update a organization.
     *
     * @param organizationDTO the entity to update
     * @return the persisted entity
     */
    OrganizationDTO update(OrganizationDTO organizationDTO);

    /**
     *  Get all the organizations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrganizationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" organization.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrganizationDTO findOne(Long id);

    /**
     *  Delete the "id" organization.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<OrganizationDTO> search(String filter, Pageable pageable);
}
