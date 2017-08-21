package com.leadlet.service;

import com.leadlet.service.dto.ObjectiveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Objective.
 */
public interface ObjectiveService {

    /**
     * Save a objective.
     *
     * @param objectiveDTO the entity to save
     * @return the persisted entity
     */
    ObjectiveDTO save(ObjectiveDTO objectiveDTO);

    /**
     *  Get all the objectives.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ObjectiveDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" objective.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ObjectiveDTO findOne(Long id);

    /**
     *  Delete the "id" objective.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
