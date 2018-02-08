package com.leadlet.service.impl;

import com.leadlet.domain.Team;
import com.leadlet.repository.TeamRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.TeamService;
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

    @Override
    public TeamDTO save(TeamDTO teamDTO) {

        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        team.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        team = teamRepository.save(team);

        return teamMapper.toDto(team);
    }

    @Override
    public TeamDTO update(TeamDTO teamDTO) {
        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        Team teamFromDb = teamRepository.findOneByIdAndAppAccount_Id(team.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (teamFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            team.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            team = teamRepository.save(team);
            return teamMapper.toDto(team);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<TeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(teamMapper::toDto);
    }

    @Override
    public TeamDTO findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return teamMapper.toDto(team);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        Team teamFromDb = teamRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());

        if (teamFromDb != null) {
            teamRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
