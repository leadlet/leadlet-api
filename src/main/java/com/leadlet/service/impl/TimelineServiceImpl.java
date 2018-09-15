package com.leadlet.service.impl;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.repository.NoteRepository;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.TimelineDTO;
import com.leadlet.service.mapper.ActivityMapper;
import com.leadlet.service.mapper.NoteMapper;
import com.leadlet.service.mapper.TimelineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    private final ElasticsearchService elasticsearchService;

    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               TimelineMapper timelineMapper,
                               NoteRepository noteRepository,
                               ActivityRepository activityRepository,
                               NoteMapper noteMapper,
                               ActivityMapper activityMapper,
                               ElasticsearchService elasticsearchService) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
        this.noteRepository = noteRepository;
        this.activityRepository = activityRepository;
        this.noteMapper = noteMapper;
        this.activityMapper = activityMapper;
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public Timeline save(Timeline timeline) {
        log.warn("save");
        return null;
    }


    @Override
    public Page<TimelineDTO> query(String searchQuery, Pageable pageable) throws IOException {

        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds(SearchConstants.TIMELINE_INDEX,searchQuery, pageable);

        Page<TimelineDTO> timelines = new PageImpl<TimelineDTO>(timelineRepository.findAllByIdIn(response.getFirst()).stream()
            .map(timelineMapper::toDto).collect(Collectors.toList()),
            pageable,
            response.getSecond())
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                }

            return timelineDTO;
        });;

        return timelines;
    }

    @Override
    @Async
    public void noteCreated(Note note) {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.NOTE_CREATED);

        if (note.getPerson() != null) {
            timelineItem.setPerson(note.getPerson());
        }

        if (note.getDeal() != null) {
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
        timelineItem.setPerson(activity.getPerson());
        timelineItem.setAppAccount(activity.getAppAccount());
        timelineItem.setSourceId(activity.getId());
        timelineItem.setUser(activity.getAgent());
        timelineItem.setDeal(activity.getDeal());

        timelineRepository.save(timelineItem);
    }
}
