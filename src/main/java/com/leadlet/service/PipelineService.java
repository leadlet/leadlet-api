package com.leadlet.service;

import com.leadlet.service.dto.PipelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Pipeline.
 */
public interface PipelineService {

    /**
     * Save a pipeline.
     *
     * @param pipelineDTO the entity to save
     * @return the persisted entity
     */
    PipelineDTO save(PipelineDTO pipelineDTO);

    /**
     *  Get all the pipelines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PipelineDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pipeline.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PipelineDTO findOne(Long id);

    /**
     *  Delete the "id" pipeline.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
