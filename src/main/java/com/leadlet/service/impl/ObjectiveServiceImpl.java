package com.leadlet.service.impl;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.User;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.repository.UserRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ObjectiveService;
import com.leadlet.domain.Objective;
import com.leadlet.repository.ObjectiveRepository;
import com.leadlet.service.dto.ObjectiveDTO;
import com.leadlet.service.dto.TeamObjectiveDTO;
import com.leadlet.service.mapper.ObjectiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Objective.
 */
@Service
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {

    private final Logger log = LoggerFactory.getLogger(ObjectiveServiceImpl.class);

    private final ObjectiveRepository objectiveRepository;

    private final AppAccountRepository appAccountRepository;

    private final UserRepository userRepository;

    private final ObjectiveMapper objectiveMapper;

    public ObjectiveServiceImpl(ObjectiveRepository objectiveRepository, ObjectiveMapper objectiveMapper, UserRepository userRepository, AppAccountRepository appAccountRepository) {
        this.objectiveRepository = objectiveRepository;
        this.objectiveMapper = objectiveMapper;
        this.userRepository = userRepository;
        this.appAccountRepository = appAccountRepository;
    }

    /**
     * Save a objective.
     *
     * @param objectiveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObjectiveDTO save(ObjectiveDTO objectiveDTO) {
        log.debug("Request to save Objective : {}", objectiveDTO);
        Objective objective = objectiveMapper.toEntity(objectiveDTO);
        objective = objectiveRepository.save(objective);
        return objectiveMapper.toDto(objective);
    }

    @Override
    public TeamObjectiveDTO saveTeamObjective(TeamObjectiveDTO teamObjectiveDTO) {

        log.debug("Request to save Objective: {}", teamObjectiveDTO);

        List<User> users = userRepository.findAllByTeam_IdAndAppAccount_Id(teamObjectiveDTO.getTeamId(), SecurityUtils.getCurrentUserAppAccountId());

        for (User user : users) {
            Objective objective = objectiveRepository.findOneByNameAndUserAndAppAccount_Id(teamObjectiveDTO.getName(), user, SecurityUtils.getCurrentUserAppAccountId());

            if (objective == null) {
                objective = new Objective();
                objective.setName(teamObjectiveDTO.getName());
                objective.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
                objective.setUser(user);
            }
            objective.setDailyAmount(teamObjectiveDTO.getDailyAmount() / users.size());
            objective.setWeeklyAmount(teamObjectiveDTO.getWeeklyAmount() / users.size());
            objective.setMonthlyAmount(teamObjectiveDTO.getMonthlyAmount() / users.size());

            objective = objectiveRepository.save(objective);
        }
        List<User> updatedUsers = userRepository.findAllByTeam_IdAndAppAccount_Id(teamObjectiveDTO.getTeamId(), SecurityUtils.getCurrentUserAppAccountId());
        TeamObjectiveDTO newTeamObjectiveDTO = new TeamObjectiveDTO();
        newTeamObjectiveDTO.setTeamId(teamObjectiveDTO.getTeamId());
        newTeamObjectiveDTO.setName(teamObjectiveDTO.getName());

        for (User user : updatedUsers) {
            for (Objective objective : user.getObjectives()) {
                if (objective.getName().equals(teamObjectiveDTO.getName())) {
                    newTeamObjectiveDTO.setDailyAmount(newTeamObjectiveDTO.getDailyAmount() + objective.getDailyAmount());
                    newTeamObjectiveDTO.setWeeklyAmount(newTeamObjectiveDTO.getWeeklyAmount() + objective.getWeeklyAmount());
                    newTeamObjectiveDTO.setMonthlyAmount(newTeamObjectiveDTO.getMonthlyAmount() + objective.getMonthlyAmount());
                }
            }
        }
        return newTeamObjectiveDTO;
    }

    /**
     * Get all the objectives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObjectiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Objectives");
        return objectiveRepository.findAll(pageable)
            .map(objectiveMapper::toDto);
    }

    /**
     * Get one objective by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ObjectiveDTO findOne(Long id) {
        log.debug("Request to get Objective : {}", id);
        Objective objective = objectiveRepository.findOne(id);
        return objectiveMapper.toDto(objective);
    }

    /**
     * Delete the  objective by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Objective : {}", id);
        objectiveRepository.delete(id);
    }
}
