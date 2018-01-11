package com.leadlet.service;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.dto.TimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

/**
 * Service Interface for managing Activity.
 */
public interface TimelineService {

    Timeline save(Timeline timeline);

    /**
     *  Get all the activities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TimelineDTO> findAll(Pageable pageable);

    /**
     *  Get all the timelines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Timeline> findByContactId(Long contactId, Pageable pageable);

    /**
     *  Get all the timelines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Timeline> findByUserId(Long userId, Pageable pageable);

    @Async
    void noteCreated(Note note);

    @Async
    void activityCreated(Activity activity);
}
