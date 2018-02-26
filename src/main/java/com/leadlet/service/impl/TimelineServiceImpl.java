package com.leadlet.service.impl;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.repository.NoteRepository;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.TimelineDTO;
import com.leadlet.service.mapper.ActivityMapper;
import com.leadlet.service.mapper.NoteMapper;
import com.leadlet.service.mapper.TimelineMapper;
import com.leadlet.web.rest.NoteResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Timeline.
 */
@Service
@Transactional
public class TimelineServiceImpl implements TimelineService {

    private final Logger log = LoggerFactory.getLogger(TimelineServiceImpl.class);

    private final TimelineRepository timelineRepository;
    private final TimelineMapper timelineMapper;

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final ActivityMapper activityMapper;


    private final ActivityRepository activityRepository;


    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               TimelineMapper timelineMapper,
                               NoteRepository noteRepository,
                               ActivityRepository activityRepository,
                               NoteMapper noteMapper,
                               ActivityMapper activityMapper) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
        this.noteRepository = noteRepository;
        this.activityRepository = activityRepository;
        this.noteMapper = noteMapper;
        this.activityMapper = activityMapper;

    }

    @Override
    public Timeline save(Timeline timeline) {
        log.warn("save");
        return null;
    }

    @Override
    public Page<TimelineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        return timelineRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(timelineMapper::toDto)
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

                return timelineDTO;
            });
    }

    @Override
    public Page<TimelineDTO> findByPersonId(Long personId, Pageable pageable) {
        return timelineRepository.findByPerson_IdAndAppAccount_Id(personId, SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(timelineMapper::toDto)
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

                return timelineDTO;
            });
    }

    @Override
    public Page<TimelineDTO> findByOrganizationId(Long organizationId, Pageable pageable) {
        return timelineRepository.findByOrganization_IdAndAppAccount_Id(organizationId,SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(timelineMapper::toDto)
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

                return timelineDTO;
            });
    }

    @Override
    public Page<TimelineDTO> findByDealId(Long dealId, Pageable pageable) {

        return timelineRepository.findByDeal_IdAndAppAccount_Id(dealId,SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(timelineMapper::toDto)
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

                return timelineDTO;
            });
    }

    @Override
    public Page<TimelineDTO> findByUserId(Long userId, Pageable pageable) {

        return timelineRepository.findByUser_IdAndAppAccount_Id(userId,SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(timelineMapper::toDto)
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

                return timelineDTO;
            });
    }

    @Override
    @Async
    public void noteCreated(Note note) {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.NOTE_CREATED);

        if (note.getPerson() != null) {
            timelineItem.setPerson(note.getPerson());
        }
        if(note.getOrganization() != null){
            timelineItem.setOrganization(note.getOrganization());
        }
        if(note.getDeal() != null){
            timelineItem.setDeal(note.getDeal());
        }
        if (note.getAppAccount() != null) {
            timelineItem.setAppAccount(note.getAppAccount());
        }

        // TODO note id should be there
        timelineItem.setSourceId(note.getId());

        //timelineItem.setUser();

        timelineRepository.save(timelineItem);

    }

    @Override
    @Async
    public void activityCreated(Activity activity) {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.ACTIVITY_CREATED);

        timelineItem.setOrganization(activity.getOrganization());
        timelineItem.setPerson(activity.getPerson());
        timelineItem.setAppAccount(activity.getAppAccount());
        timelineItem.setSourceId(activity.getId());
        timelineItem.setUser(activity.getUser());

        timelineRepository.save(timelineItem);
    }
}
