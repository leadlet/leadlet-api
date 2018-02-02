package com.leadlet.service;

import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealDetailDTO;
import com.leadlet.service.dto.DealMoveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    DealDetailDTO save(DealDTO dealDTO);

    /**
     * Update a deal.
     *
     * @param dealDTO the entity to update
     * @return the persisted entity
     */
    DealDetailDTO update(DealDTO dealDTO);

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
    DealDetailDTO findOne(Long id);

    /**
     * Delete the "id" deal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Save a deal.
     *
     * @param dealMoveDTO the entity to save
     * @return the persisted entity
     */
    DealDetailDTO move(DealMoveDTO dealMoveDTO);

    Page<DealDTO> findAllByStageId(Long stageId, Pageable pageable);

    Double getDealTotalByStage(Long stageId);

    Page<DealDTO> search(String filter, Pageable pageable);
}
