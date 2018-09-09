package com.leadlet.service;

import com.leadlet.service.dto.LostReasonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LostReasonService {

    /**
     * Save a lost reason.
     *
     * @param lostReasonDTO the entity to save
     * @return the persisted entity
     */
    LostReasonDTO save(LostReasonDTO lostReasonDTO);

    /**
     * Update a lost reason.
     *
     * @param lostReasonDTO the entity to update
     * @return the persisted entity
     */
    LostReasonDTO update(LostReasonDTO lostReasonDTO);

    /**
     * Get all the lost reasons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LostReasonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" lost reason.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LostReasonDTO findOne(Long id);

    /**
     * Delete the "id" lost reason.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
