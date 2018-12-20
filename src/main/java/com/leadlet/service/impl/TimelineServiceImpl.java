package com.leadlet.service.impl;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.repository.DealRepository;
import com.leadlet.repository.NoteRepository;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.TimelineDTO;
import com.leadlet.service.mapper.ActivityMapper;
import com.leadlet.service.mapper.DealMapper;
import com.leadlet.service.mapper.NoteMapper;
import com.leadlet.service.mapper.TimelineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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

    private final DealMapper dealMapper;


    private final ActivityRepository activityRepository;

    private final DealRepository dealRepository;

    private final ElasticsearchService elasticsearchService;

    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               TimelineMapper timelineMapper,
                               NoteRepository noteRepository,
                               ActivityRepository activityRepository,
                               NoteMapper noteMapper,
                               ActivityMapper activityMapper,
                               DealRepository dealRepository,
                               DealMapper dealMapper,
                               ElasticsearchService elasticsearchService) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
        this.noteRepository = noteRepository;
        this.activityRepository = activityRepository;
        this.noteMapper = noteMapper;
        this.activityMapper = activityMapper;
        this.elasticsearchService = elasticsearchService;
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;

    }

    @Override
    public Timeline save(Timeline timeline) {
        log.warn("save");
        return null;
    }


    @Override
    public Page<TimelineDTO> findAll(String searchQuery, Pageable pageable) throws IOException {

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if(StringUtils.isEmpty(searchQuery)){
            searchQuery = appAccountFilter;
        }else {
            searchQuery += " AND " + appAccountFilter;
        }
        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds(SearchConstants.TIMELINE_INDEX,searchQuery, pageable);

        List<TimelineDTO> unsorted = timelineRepository.findAllByIdIn(response.getFirst()).stream()
            .map(timelineMapper::toDto).collect(Collectors.toList());
        List<Long> sortedIds = response.getFirst();

        // we are getting ids from ES sorted but JPA returns result not sorted
        // below code-piece sorts the returned DTOs to have same sort with ids.
        Collections.sort(unsorted,  new Comparator<TimelineDTO>() {
            public int compare(TimelineDTO left, TimelineDTO right) {
                return Integer.compare(sortedIds.indexOf(left.getId()), sortedIds.indexOf(right.getId()));
            }
        } );


        Page<TimelineDTO> timelines = new PageImpl<TimelineDTO>(unsorted,
            pageable,
            response.getSecond())
            .map(timelineDTO -> {
                if (timelineDTO.getType().equals(TimelineItemType.NOTE_CREATED)) {
                    Note note = noteRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(noteMapper.toDto(note));
                } else if (timelineDTO.getType().equals(TimelineItemType.ACTIVITY_CREATED)) {
                    Activity activity = activityRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(activityMapper.toDto(activity));
                } else if (timelineDTO.getType().equals(TimelineItemType.DEAL_CREATED)) {
                    Deal deal = dealRepository.getOne(timelineDTO.getSourceId());
                    timelineDTO.setSource(dealMapper.toDto(deal));
                }

            return timelineDTO;
        });;

        return timelines;
    }

    @Override
    public void noteCreated(Note note) throws IOException {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.NOTE_CREATED);

        if (note.getContact() != null) {
            timelineItem.setContact(note.getContact());
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
        elasticsearchService.indexTimeline(timelineItem);

    }

    @Override
    public void activityCreated(Activity activity) {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.ACTIVITY_CREATED);
        timelineItem.setContact(activity.getContact());
        timelineItem.setAppAccount(activity.getAppAccount());
        timelineItem.setSourceId(activity.getId());
        timelineItem.setAgent(activity.getAgent());
        timelineItem.setDeal(activity.getDeal());

        timelineRepository.save(timelineItem);

    }

    @Override
    public void dealCreated(Deal deal) {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.DEAL_CREATED);
        timelineItem.setContact(deal.getContact());
        timelineItem.setAppAccount(deal.getAppAccount());
        timelineItem.setSourceId(deal.getId());
        timelineItem.setAgent(deal.getAgent());
        timelineItem.setDeal(deal);

        timelineRepository.save(timelineItem);

    }

}
