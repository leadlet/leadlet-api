package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityService;
import com.leadlet.domain.Activity;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Activity.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService{

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
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

        if(activity.getAppAccount().equals(SecurityUtils.getCurrentUserAppAccount())){
            activity.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
            activity = activityRepository.save(activity);
            return activityMapper.toDto(activity);
        }
        return activityDTO;
    }

    /**
     *  Get all the activities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        return activityRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(activityMapper::toDto);
    }

    /**
     *  Get one activity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityDTO findOne(Long id) {
        log.debug("Request to get Activity : {}", id);
        Activity activity = activityRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return activityMapper.toDto(activity);
    }

    /**
     *  Delete the  activity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.deleteByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
    }
}
