package com.leadlet.scheduled;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.TimelineRepository;
import com.leadlet.service.dto.TimelineSearchIndexDTO;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Team.
 */
@Profile({"prod", "dev"})
@Service
@Transactional
public class TimelineIndexService {
    private final Logger log = LoggerFactory.getLogger(TimelineIndexService.class);

    private final RestHighLevelClient restHighLevelClient;
    private final TimelineRepository timelineRepository;

    public TimelineIndexService(RestHighLevelClient restHighLevelClient, TimelineRepository timelineRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.timelineRepository = timelineRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void syncTimelines() {

        int pageNo = 0;

        boolean getPages = true;

        while(getPages){
            Pageable pageable = new PageRequest(pageNo, 20);

            Page<Timeline> timelinePage = timelineRepository.findAllBySyncStatusAndCreatedDateLessThan(SyncStatus.NOT_SYNCED, Instant.now(), pageable);

            if( timelinePage.getContent() == null ||  timelinePage.getContent().size() == 0){
                break;
            }

            try {
                copyTimelinesToElasticSearch(timelinePage.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }


            pageNo++;
        }


    }

    @Transactional
    void copyTimelinesToElasticSearch(List<Timeline> timelines) throws IOException {
        // Set deal sync status

        List<Long> timelineIds = timelines.stream().map( timeline -> timeline.getId()).collect(Collectors.toList());

        timelineRepository.updateTimelinesStatus(SyncStatus.IN_PROGRESS, timelineIds);

        sendRecordsToElasticSearch( timelines );

        timelineRepository.updateTimelinesStatus(SyncStatus.SYNCED, timelineIds);



    }

    private void sendRecordsToElasticSearch(List<Timeline> timelines) throws IOException {
        BulkRequest request = new BulkRequest();

        for ( Timeline timeline: timelines) {
            TimelineSearchIndexDTO timelineSearchIndexDTO = new TimelineSearchIndexDTO(timeline);

            request.add(new IndexRequest(SearchConstants.TIMELINE_INDEX, SearchConstants.TIMELINE_TYPE,  String.valueOf(timelineSearchIndexDTO.getId()))
                .source(timelineSearchIndexDTO.getBuilder()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);

        log.info(response.status().toString());
        log.info(response.buildFailureMessage());

    }

}
