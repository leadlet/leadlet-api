package com.leadlet.service;

import com.leadlet.service.dto.StageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Stage.
 */
public interface StageService {

    /**
     * Save a stage.
     *
     * @param stageDTO the entity to save
     * @return the persisted entity
     */
    StageDTO save(StageDTO stageDTO);

    /**
     * Update a stage.
     *
     * @param stageDTO the entity to update
     * @return the persisted entity
     */
    StageDTO update(StageDTO stageDTO);

    /**
     *  Get all the stages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StageDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" stage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StageDTO findOne(Long id);

    List<StageDTO> findAllByPipelineId(Long pipelineId);

    /**
     *  Delete the "id" stage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
