package com.leadlet.service;

import com.leadlet.service.dto.DealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Deal.
 */
public interface DealService {

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    DealDTO save(DealDTO dealDTO);

    /**
     * Update a deal.
     *
     * @param dealDTO the entity to update
     * @return the persisted entity
     */
    DealDTO update(DealDTO dealDTO);

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DealDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DealDTO findOne(Long id);

    /**
     * Delete the "id" deal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
