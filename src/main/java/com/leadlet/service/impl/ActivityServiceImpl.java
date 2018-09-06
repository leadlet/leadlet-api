package com.leadlet.service.impl;

import com.leadlet.domain.Activity;
import com.leadlet.domain.ActivityAggregation;
import com.leadlet.domain.enumeration.ActivityType;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityService;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.dto.TeamObjectiveDTO;
import com.leadlet.service.mapper.ActivityMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
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
        return activityRepository.findByAgent_Id(userId, pageable)
            .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByDealId(Long dealId, Pageable pageable) {
        log.debug("Request to get all Activities for Deal");
        return activityRepository.findByDeal_Id(dealId, pageable)
            .map(activityMapper::toDto);
    }

    @Override
    public List<TeamObjectiveDTO> getActivityCompletionSummary(Long teamId) {
        List<TeamObjectiveDTO> completedActivities = new ArrayList<>();

        DateTime startOfDay = new DateTime().withTimeAtStartOfDay();
        DateTime startOfWeek = new DateTime().withDayOfWeek(1).withTimeAtStartOfDay();
        DateTime startOfMonth = new DateTime().withDayOfMonth(1).withTimeAtStartOfDay();

        Map<ActivityType, Long> dailyCompleted = convertListToMap(activityRepository.calculateCompletedActivitiesTeamBetweenDates(teamId, startOfDay.toDate(), new Date()));
        Map<ActivityType, Long> weeklyCompleted = convertListToMap(activityRepository.calculateCompletedActivitiesTeamBetweenDates(teamId, startOfWeek.toDate(), new Date()));
        Map<ActivityType, Long> monthlyCompleted = convertListToMap(activityRepository.calculateCompletedActivitiesTeamBetweenDates(teamId, startOfMonth.toDate(), new Date()));


        for( ActivityType type: ActivityType.values()){
            TeamObjectiveDTO teamObjectiveForType = new TeamObjectiveDTO();
            teamObjectiveForType.setTeamId(teamId);
            teamObjectiveForType.setName(type);
            teamObjectiveForType.setDailyAmount(dailyCompleted.get(type) != null ? dailyCompleted.get(type): 0L);
            teamObjectiveForType.setWeeklyAmount(weeklyCompleted.get(type) != null ? weeklyCompleted.get(type)  : 0L);
            teamObjectiveForType.setMonthlyAmount(monthlyCompleted.get(type) != null ? monthlyCompleted.get(type): 0L);
            completedActivities.add(teamObjectiveForType);
        }

        return completedActivities;

    }

    private HashMap<ActivityType,Long> convertListToMap(List<ActivityAggregation> completedActivities){
        HashMap<ActivityType,Long> hmap = new HashMap<>();

        for(ActivityAggregation entry: completedActivities){
            hmap.put(entry.getActivityType(),entry.getCount());
        }

        return  hmap;
    }

    @Override
    public Page<ActivityDTO> search(String searchQuery, Pageable pageable) throws IOException {

        // TODO add appaccountid

        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds("leadlet-activity", searchQuery, pageable);

        Page<ActivityDTO> activityDTOS = new PageImpl<ActivityDTO>(activityRepository.findAllByIdIn(response.getFirst()).stream()
            .map(activityMapper::toDto).collect(Collectors.toList()),
            pageable,
            response.getSecond());

        return activityDTOS;
    }
}
