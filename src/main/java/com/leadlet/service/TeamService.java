package com.leadlet.service;

import com.leadlet.service.dto.TeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     *
     * @param teamDTO the entity to save
     * @return the persisted entity
     */
    TeamDTO save(TeamDTO teamDTO);

    /**
     * Update a team.
     *
     * @param teamDTO the entity to update
     * @return the persisted entity
     */
    TeamDTO update(TeamDTO teamDTO);

    /**
     *  Get all the teams.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TeamDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" team.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TeamDTO findOne(Long id);

    /**
     *  Delete the "id" team.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
