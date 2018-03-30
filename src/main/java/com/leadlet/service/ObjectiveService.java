package com.leadlet.service;

import com.leadlet.service.dto.ObjectiveDTO;
import com.leadlet.service.dto.TeamObjectiveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
     * Save a objective.
     *
     * @param teamObjectiveDTO the entity to save
     * @return the persisted entity
     */
    TeamObjectiveDTO saveTeamObjective(TeamObjectiveDTO teamObjectiveDTO);

    /**
     * Save a objective.
     *
     * @param objectiveDTO the entity to save
     * @return the persisted entity
     */
    ObjectiveDTO saveUserObjective(ObjectiveDTO objectiveDTO);

    /**
     *  Get all the objectives.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ObjectiveDTO> findAll(Pageable pageable);

    /**
     *  Get all the objectives By Team Id.

     *  @return the list of entities
     */
    List<TeamObjectiveDTO> findAllByTeamId(Long teamId);

    /**
     *  Get all the objectives By User Id.

     *  @return the list of entities
     */
    List<ObjectiveDTO> findAllByUserId(Long userId);

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
