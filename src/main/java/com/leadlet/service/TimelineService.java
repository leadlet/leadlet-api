package com.leadlet.service;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Note;
import com.leadlet.service.dto.DetailedDealDTO;
import com.leadlet.service.dto.TimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service Interface for managing Activity.
 */
public interface TimelineService {

    Page<TimelineDTO> query(String searchQuery, Pageable pageable) throws IOException;

    void noteCreated(Note note) throws IOException;

    void activityCreated(Activity activity) throws IOException;

    void dealCreated(Deal deal) throws IOException;

    void dealUpdated(DetailedDealDTO dealOldDto, DetailedDealDTO dealNewDto, Deal dealNew) throws IOException;
}
