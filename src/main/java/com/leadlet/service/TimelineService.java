package com.leadlet.service;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

/**
 * Service Interface for managing Activity.
 */
public interface TimelineService {

    Timeline save(Timeline timeline);

    Page<TimelineDTO> query(String searchQuery, Pageable pageable) throws IOException;


    @Async
    void noteCreated(Note note);

    @Async
    void activityCreated(Activity activity);
}
