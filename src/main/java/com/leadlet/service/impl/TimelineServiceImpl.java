package com.leadlet.service.impl;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ActivityService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Time;


/**
 * Service Implementation for managing Activity.
 */
@Service
@Transactional
public class TimelineServiceImpl implements TimelineService {

    private final Logger log = LoggerFactory.getLogger(TimelineServiceImpl.class);

    private final TimelineRepository timelineRepository;

    public TimelineServiceImpl(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    @Override
    public Timeline save(Timeline timeline) {
        log.warn("save");
        return null;
    }

    @Override
    public Page<Timeline> findAll(Pageable pageable) {
        log.warn("findAll");
        return null;
    }

    @Override
    public Page<Timeline> findByContactId(Long contactId, Pageable pageable) {
        log.warn("findByContactId");
        return null;
    }

    @Override
    public Page<Timeline> findByUserId(Long userId, Pageable pageable) {
        log.warn("findByUserId");
        return null;
    }

    @Override
    @Async
    public void noteCreated(Note note) {
        Timeline timelineItem = new Timeline();

        timelineItem.setType(TimelineItemType.NOTE_CREATED);

        if(note.getContact() != null ){
            timelineItem.setContact(note.getContact());
        }

        if(note.getAppAccount() != null ){
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

    }
}
