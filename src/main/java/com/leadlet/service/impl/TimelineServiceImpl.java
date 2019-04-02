package com.leadlet.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.leadlet.service.dto.EntityChangeLogDTO;
import com.leadlet.service.dto.TimelineDTO;
import com.leadlet.service.mapper.*;
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
import java.util.*;
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
    private final DetailedDealMapper detailedDealMapper;
    private final ActivityRepository activityRepository;
    private final DealRepository dealRepository;
    private final ElasticsearchService elasticsearchService;
    private final UserMapper userMapper;
    private final PipelineMapper pipelineMapper;
    private final DealValueMapper dealValueMapper;
    private final StageMapper stageMapper;
    private final ContactMapper contactMapper;

    ObjectMapper mapper = new ObjectMapper();

    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               TimelineMapper timelineMapper,
                               NoteRepository noteRepository,
                               ActivityRepository activityRepository,
                               NoteMapper noteMapper,
                               ActivityMapper activityMapper,
                               UserMapper userMapper,
                               DealRepository dealRepository,
                               DetailedDealMapper detailedDealMapper,
                               ElasticsearchService elasticsearchService,
                               PipelineMapper pipelineMapper,
                               DealValueMapper dealValueMapper,
                               StageMapper stageMapper,
                               ContactMapper contactMapper) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
        this.noteRepository = noteRepository;
        this.activityRepository = activityRepository;
        this.noteMapper = noteMapper;
        this.activityMapper = activityMapper;
        this.elasticsearchService = elasticsearchService;
        this.dealRepository = dealRepository;
        this.detailedDealMapper = detailedDealMapper;
        this.userMapper = userMapper;
        this.pipelineMapper = pipelineMapper;
        this.dealValueMapper = dealValueMapper;
        this.stageMapper = stageMapper;
        this.contactMapper = contactMapper;

    }

    @Override
    public Page<TimelineDTO> query(String searchQuery, Pageable pageable) throws IOException {

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if (StringUtils.isEmpty(searchQuery)) {
            searchQuery = appAccountFilter;
        } else {
            searchQuery += " AND " + appAccountFilter;
        }
        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds(SearchConstants.TIMELINE_INDEX, searchQuery, pageable);

        List<TimelineDTO> unsorted = timelineRepository.findAllByIdIn(response.getFirst()).stream()
            .map(timelineMapper::toDto).collect(Collectors.toList());
        List<Long> sortedIds = response.getFirst();

        // we are getting ids from ES sorted but JPA returns result not sorted
        // below code-piece sorts the returned DTOs to have same sort with ids.
        Collections.sort(unsorted, new Comparator<TimelineDTO>() {
            public int compare(TimelineDTO left, TimelineDTO right) {
                return Integer.compare(sortedIds.indexOf(left.getId()), sortedIds.indexOf(right.getId()));
            }
        });


        Page<TimelineDTO> timelines = new PageImpl<TimelineDTO>(unsorted,
            pageable,
            response.getSecond());

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

        String contentJSON = mapper.writeValueAsString(noteMapper.toDto(note));
        timelineItem.setContent(contentJSON);

        timelineRepository.save(timelineItem);
        elasticsearchService.indexTimeline(timelineItem);

    }

    @Override
    public void activityCreated(Activity activity) throws IOException {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.ACTIVITY_CREATED);
        timelineItem.setContact(activity.getContact());
        timelineItem.setAppAccount(activity.getAppAccount());
        timelineItem.setAgent(activity.getAgent());
        timelineItem.setDeal(activity.getDeal());

        String contentJSON = mapper.writeValueAsString(activityMapper.toDto(activity));
        timelineItem.setContent(contentJSON);

        timelineRepository.save(timelineItem);
        elasticsearchService.indexTimeline(timelineItem);
    }

    @Override
    public void dealCreated(Deal deal) throws IOException {

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.DEAL_CREATED);
        timelineItem.setContact(deal.getContact());
        timelineItem.setAppAccount(deal.getAppAccount());
        timelineItem.setAgent(deal.getAgent());
        timelineItem.setDeal(deal);

        String contentJSON = mapper.writeValueAsString(detailedDealMapper.toDto(deal));
        timelineItem.setContent(contentJSON);

        timelineRepository.save(timelineItem);
        elasticsearchService.indexTimeline(timelineItem);
    }

    @Override
    public void dealUpdated(Deal oldDeal, Deal newDeal) throws IOException {
        List<EntityChangeLogDTO> changeLogs = buildChangeLogForDealUpdate(oldDeal, newDeal);

        String contentJSON = mapper.writeValueAsString(changeLogs);

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.DEAL_UPDATED);
        timelineItem.setAppAccount(newDeal.getAppAccount());
        timelineItem.setAgent(newDeal.getAgent());
        timelineItem.setDeal(newDeal);
        timelineItem.setContent(contentJSON);

        timelineRepository.save(timelineItem);
        elasticsearchService.indexTimeline(timelineItem);
    }

    private List<EntityChangeLogDTO> buildChangeLogForDealUpdate(Deal oldDeal, Deal newDeal) {

        List<EntityChangeLogDTO> changeLogs = new ArrayList<>();

        if(!oldDeal.getTitle().equals(newDeal.getTitle())){
            changeLogs.add(new EntityChangeLogDTO("title",oldDeal.getTitle(),newDeal.getTitle()));
        }
        if(!oldDeal.getAgent().equals(newDeal.getAgent())){
            changeLogs.add(new EntityChangeLogDTO("agent",oldDeal.getAgent(),newDeal.getAgent()));
        }
        if(!oldDeal.getStage().equals(newDeal.getStage())){
            changeLogs.add(new EntityChangeLogDTO("stage",oldDeal.getStage(),newDeal.getStage()));
        }
        if(!oldDeal.getContact().equals(newDeal.getContact())){
            changeLogs.add(new EntityChangeLogDTO("contact",oldDeal.getContact(),newDeal.getContact()));
        }
        if(!oldDeal.getDealChannel().equals(newDeal.getDealChannel())){
            changeLogs.add(new EntityChangeLogDTO("channel",oldDeal.getDealChannel(),newDeal.getDealChannel()));
        }
        if(!oldDeal.getDealSource().equals(newDeal.getDealSource())){
            changeLogs.add(new EntityChangeLogDTO("source",oldDeal.getDealSource(),newDeal.getDealSource()));
        }
        if(!oldDeal.getDealStatus().equals(newDeal.getDealStatus())){
            changeLogs.add(new EntityChangeLogDTO("status",oldDeal.getDealStatus(),newDeal.getDealStatus()));
        }
        if(!oldDeal.getDealValue().equals(newDeal.getDealValue())){
            changeLogs.add(new EntityChangeLogDTO("value",oldDeal.getDealValue(),newDeal.getDealValue()));
        }


        return changeLogs;

    }

}
