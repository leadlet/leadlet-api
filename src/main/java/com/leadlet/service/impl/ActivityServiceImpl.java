package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityService;
import com.leadlet.domain.Activity;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * Service Implementation for managing Activity.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    private final TimelineService timelineService;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper, TimelineService timelineService) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.timelineService = timelineService;
    }

    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivityDTO save(ActivityDTO activityDTO) {

        log.debug("Request to save Activity : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        activity = activityRepository.save(activity);
        timelineService.activityCreated(activity);
        return activityMapper.toDto(activity);

    }

    /**
     * Update a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivityDTO update(ActivityDTO activityDTO) {
        log.debug("Request to update Activity : {}", activityDTO);

        Activity activity = activityMapper.toEntity(activityDTO);
        Activity fromDb = activityRepository.findOneByIdAndAppAccount_Id(activity.getId(), SecurityUtils.getCurrentUserAppAccountId());
        if (fromDb != null) {
            //TODO: appaccount todo'su..
            activity.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            activity = activityRepository.save(activity);
            timelineService.activityCreated(activity);
            return activityMapper.toDto(activity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the activities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityDTO> findAll() {
        log.debug("Request to get all Activities");
        List<Activity> activityList = activityRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId());
        return activityMapper.toDto(activityList);

    }

    /**
     * Get one activity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityDTO findOne(Long id) {
        log.debug("Request to get Activity : {}", id);
        Activity activity = activityRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return activityMapper.toDto(activity);
    }

    /**
     * Delete the  activity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activity : {}", id);

        Activity objectFormDb = activityRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (objectFormDb != null) {
            activityRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByPersonId(Long personId, Pageable pageable) {
        log.debug("Request to get all Activities for Person");
        return activityRepository.findByPerson_Id(personId, pageable)
            .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByOrganizationId(Long organizationId, Pageable pageable) {
        log.debug("Request to get all Activities for Organization");
        return activityRepository.findByOrganization_Id(organizationId, pageable)
            .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByUserId(Long userId, Pageable pageable) {
        log.debug("Request to get all Activities for User");
        return activityRepository.findByUser_Id(userId, pageable)
            .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByDealId(Long dealId, Pageable pageable) {
        log.debug("Request to get all Activities for Deal");
        return activityRepository.findByDeal_Id(dealId, pageable)
            .map(activityMapper::toDto);
    }
}
