package com.leadlet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * Service Interface for managing Activity.
 */
public interface TimelineService {

    Timeline save(Timeline timeline);

    Page<TimelineDTO> query(String searchQuery, Pageable pageable) throws IOException;

    void noteCreated(Note note) throws IOException;

    void activityCreated(Activity activity);

    void dealCreated(Deal deal);

    void dealUpdated(Deal dealOld, Deal dealNew, List<String> modifiedFields) throws JsonProcessingException;
}
