package com.leadlet.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Note;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.DetailedDealDTO;
import com.leadlet.service.dto.EntityChangeLogDTO;
import com.leadlet.service.dto.TimelineDTO;
import com.leadlet.service.mapper.ActivityMapper;
import com.leadlet.service.mapper.DetailedDealMapper;
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
    private final NoteMapper noteMapper;
    private final ActivityMapper activityMapper;
    private final DetailedDealMapper detailedDealMapper;
    private final ElasticsearchService elasticsearchService;

    ObjectMapper mapper = new ObjectMapper();

    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               TimelineMapper timelineMapper,
                               NoteMapper noteMapper,
                               ActivityMapper activityMapper,
                               DetailedDealMapper detailedDealMapper,
                               ElasticsearchService elasticsearchService) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
        this.noteMapper = noteMapper;
        this.activityMapper = activityMapper;
        this.elasticsearchService = elasticsearchService;
        this.detailedDealMapper = detailedDealMapper;
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
    public void dealUpdated(DetailedDealDTO dealOldDto, DetailedDealDTO dealNewDto, Deal dealNew) throws IOException {
        List<EntityChangeLogDTO> changeLogs = buildChangeLogForDealUpdate(dealOldDto, dealNewDto);

        if(changeLogs.isEmpty()){
            return;
        }
        String contentJSON = mapper.writeValueAsString(changeLogs);

        Timeline timelineItem = new Timeline();
        timelineItem.setType(TimelineItemType.DEAL_UPDATED);
        timelineItem.setAppAccount(dealNew.getAppAccount());
        timelineItem.setAgent(dealNew.getAgent());
        timelineItem.setDeal(dealNew);
        timelineItem.setContent(contentJSON);

        timelineRepository.save(timelineItem);
        elasticsearchService.indexTimeline(timelineItem);
    }

    private List<EntityChangeLogDTO> buildChangeLogForDealUpdate(DetailedDealDTO oldDeal, DetailedDealDTO newDeal) {

        List<EntityChangeLogDTO> changeLogs = new ArrayList<>();

        if(!Objects.equals(oldDeal.getTitle(),newDeal.getTitle())){
            changeLogs.add(new EntityChangeLogDTO("title",oldDeal.getTitle(),newDeal.getTitle()));
        }
        if(!Objects.equals(oldDeal.getAgent(),newDeal.getAgent())){
            changeLogs.add(new EntityChangeLogDTO("agent",
                oldDeal.getAgent(), newDeal.getAgent()));
        }
        if(!Objects.equals(oldDeal.getStage(),newDeal.getStage())){
            changeLogs.add(new EntityChangeLogDTO("stage",
                oldDeal.getStage(), newDeal.getStage()));
        }
        if(!Objects.equals(oldDeal.getContact(),newDeal.getContact())){
            changeLogs.add(new EntityChangeLogDTO("contact",
                oldDeal.getContact(), newDeal.getContact()));
        }
        if(!Objects.equals(oldDeal.getDeal_channel(),newDeal.getDeal_channel())){
            changeLogs.add(new EntityChangeLogDTO("deal_channel",
                oldDeal.getDeal_channel(), newDeal.getDeal_channel()));
        }
        if(!Objects.equals(oldDeal.getDeal_source(),newDeal.getDeal_source())){
            changeLogs.add(new EntityChangeLogDTO("deal_source",
                oldDeal.getDeal_source(), newDeal.getDeal_source()));
        }
        if(!Objects.equals(oldDeal.getDeal_status(),newDeal.getDeal_status())){
            changeLogs.add(new EntityChangeLogDTO("deal_status",
                oldDeal.getDeal_status(),newDeal.getDeal_status()));
        }
        if(!Objects.equals(oldDeal.getDeal_value(),newDeal.getDeal_value())){
            changeLogs.add(new EntityChangeLogDTO("deal_value",
                oldDeal.getDeal_value(), newDeal.getDeal_value()));
        }
        // Means something changed in products.
        if(!Objects.equals(oldDeal.getProducts(), newDeal.getProducts())){
            changeLogs.add(new EntityChangeLogDTO("products",
                oldDeal.getProducts(), newDeal.getProducts()));
        }

        if(!Objects.equals(oldDeal.getPossible_close_date(), newDeal.getPossible_close_date())){
            changeLogs.add(new EntityChangeLogDTO("possible_close_date",
                oldDeal.getPossible_close_date().toInstant().toEpochMilli(),
                newDeal.getPossible_close_date().toInstant().toEpochMilli()));
        }

        return changeLogs;

    }

}
