package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
import com.leadlet.service.TeamService;
import com.leadlet.domain.Team;
import com.leadlet.repository.TeamRepository;
import com.leadlet.service.dto.TeamDTO;
import com.leadlet.service.mapper.TeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    /**
     * Save a team.
     *
     * @param teamDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamDTO save(TeamDTO teamDTO) {
        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        team.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Update a team.
     *
     * @param teamDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public TeamDTO update(TeamDTO teamDTO) {
        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);

        Team teamFromDb = teamRepository.findOneByIdAndAppAccount(team.getId(), SecurityUtils.getCurrentUserAppAccount());

        if (teamFromDb != null) {
            team.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
            team = teamRepository.save(team);
            return teamMapper.toDto(team);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(teamMapper::toDto);
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TeamDTO findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return teamMapper.toDto(team);
    }

    /**
     * Delete the  team by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        Team teamFromDb = teamRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());

        if (teamFromDb != null) {
            teamRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
