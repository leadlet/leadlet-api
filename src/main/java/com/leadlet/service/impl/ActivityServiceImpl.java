package com.leadlet.service.impl;

import com.leadlet.domain.Activity;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityService;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    private final ElasticsearchService elasticsearchService;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper,
                               TimelineService timelineService, ElasticsearchService elasticsearchService) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.timelineService = timelineService;
        this.elasticsearchService = elasticsearchService;
    }

    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivityDTO save(ActivityDTO activityDTO) throws IOException {

        log.debug("Request to save Activity : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        activity = activityRepository.save(activity);
        timelineService.activityCreated(activity);
        elasticsearchService.indexActivity(activity);
        return activityMapper.toDto(activity);

    }

    /**
     * Update a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivityDTO update(ActivityDTO activityDTO) throws IOException {
        log.debug("Request to update Activity : {}", activityDTO);

        Activity activity = activityMapper.toEntity(activityDTO);
        Activity fromDb = activityRepository.findOneByIdAndAppAccount_Id(activity.getId(), SecurityUtils.getCurrentUserAppAccountId());
        if (fromDb != null) {
            //TODO: appaccount todo'su..
            activity.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            if(!fromDb.isDone() && activityDTO.isDone()){
                activity.setClosedDate(Instant.now());
            }
            activity = activityRepository.save(activity);
            timelineService.activityCreated(activity);
            return activityMapper.toDto(activity);
        } else {
            throw new EntityNotFoundException();
        }
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
    public Page<ActivityDTO> search(String searchQuery, Pageable pageable) throws IOException {

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if(StringUtils.isEmpty(searchQuery)){
            searchQuery = appAccountFilter;
        }else {
            searchQuery += " AND " + appAccountFilter;
        }
        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds("leadlet-activity", searchQuery, pageable);

        List<ActivityDTO> unsorted = activityRepository.findAllByIdIn(response.getFirst()).stream()
            .map(activityMapper::toDto).collect(Collectors.toList());
        List<Long> sortedIds = response.getFirst();

        // we are getting ids from ES sorted but JPA returns result not sorted
        // below code-piece sorts the returned DTOs to have same sort with ids.
        Collections.sort(unsorted,  new Comparator<ActivityDTO>() {
            public int compare(ActivityDTO left, ActivityDTO right) {
                return Integer.compare(sortedIds.indexOf(left.getId()), sortedIds.indexOf(right.getId()));
            }
        } );

        Page<ActivityDTO> activityDTOS = new PageImpl<ActivityDTO>(unsorted,
            pageable,
            response.getSecond());

        return activityDTOS;
    }
}
