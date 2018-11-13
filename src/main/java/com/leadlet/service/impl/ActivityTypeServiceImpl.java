package com.leadlet.service.impl;

import com.leadlet.domain.ActivityType;
import com.leadlet.repository.ActivityTypeRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityTypeService;
import com.leadlet.service.dto.ActivityTypeDTO;
import com.leadlet.service.mapper.ActivityTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing ActivityType.
 */
@Service
@Transactional
public class ActivityTypeServiceImpl implements ActivityTypeService {


    private final Logger log = LoggerFactory.getLogger(ActivityTypeServiceImpl.class);

    private final ActivityTypeRepository activityTypeRepository;

    private final ActivityTypeMapper activityTypeMapper;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository, ActivityTypeMapper activityTypeMapper) {
        this.activityTypeRepository = activityTypeRepository;
        this.activityTypeMapper = activityTypeMapper;
    }

    /**
     * Save a activityType.
     *
     * @param activityTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivityTypeDTO save(ActivityTypeDTO activityTypeDTO) {
        log.debug("Request to save Activity Type : {}", activityTypeDTO);
        ActivityType activityType = activityTypeMapper.toEntity(activityTypeDTO);
        activityType.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        activityType = activityTypeRepository.save(activityType);
        return activityTypeMapper.toDto(activityType);
    }

    /**
     * Update a activityType.
     *
     * @param activityTypeDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ActivityTypeDTO update(ActivityTypeDTO activityTypeDTO) {
        log.debug("Request to save Activity Type : {}", activityTypeDTO);
        ActivityType activityType = activityTypeMapper.toEntity(activityTypeDTO);
        ActivityType activityTypeFromDb = activityTypeRepository.findOneByIdAndAppAccount_Id(activityType.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (activityTypeFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            activityType.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            activityType = activityTypeRepository.save(activityType);
            return activityTypeMapper.toDto(activityType);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the activityTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Activity Types");
        return activityTypeRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(activityTypeMapper::toDto);
    }

    /**
     * Get one activityType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActivityTypeDTO findOne(Long id) {
        log.debug("Request to get Activity Type : {}", id);
        ActivityType activityType = activityTypeRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return activityTypeMapper.toDto(activityType);
    }

    /**
     * Delete the activityType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activity Type : {}", id);
        ActivityType activityTypeFromDb = activityTypeRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (activityTypeFromDb != null) {
            activityTypeRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
