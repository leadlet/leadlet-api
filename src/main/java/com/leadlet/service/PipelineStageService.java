package com.leadlet.service;

import com.leadlet.service.dto.PipelineStageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PipelineStage.
 */
public interface PipelineStageService {

    /**
     * Save a pipelineStage.
     *
     * @param pipelineStageDTO the entity to save
     * @return the persisted entity
     */
    PipelineStageDTO save(PipelineStageDTO pipelineStageDTO);

    /**
     *  Get all the pipelineStages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PipelineStageDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pipelineStage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PipelineStageDTO findOne(Long id);

    /**
     *  Delete the "id" pipelineStage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
