package com.leadlet.service;

import com.leadlet.service.dto.SourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SourceService {

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save
     * @return the persisted entity
     */
    SourceDTO save(SourceDTO sourceDTO);

    /**
     * Update a source.
     *
     * @param sourceDTO the entity to update
     * @return the persisted entity
     */
    SourceDTO update(SourceDTO sourceDTO);

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" source.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SourceDTO findOne(Long id);

    /**
     * Delete the "id" source.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
