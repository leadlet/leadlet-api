package com.leadlet.scheduled;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Activity;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.ActivityRepository;
import com.leadlet.service.dto.ActivitySearchIndexDTO;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
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
public class ActivityIndexService {

    private final RestHighLevelClient restHighLevelClient;
    private final ActivityRepository activityRepository;

    public ActivityIndexService(RestHighLevelClient restHighLevelClient, ActivityRepository activityRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.activityRepository = activityRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void syncDeals() {

        int pageNo = 0;

        boolean getPages = true;

        while(getPages){
            Pageable pageable = new PageRequest(pageNo, 20);

            Page<Activity> activities = activityRepository.findAllBySyncStatusAndCreatedDateLessThan(SyncStatus.NOT_SYNCED, Instant.now(), pageable);

            if( activities.getContent() == null ||  activities.getContent().size() == 0){
                break;
            }

            try {
                copyDealsToElasticSearch(activities.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }


            pageNo++;
        }


    }

    @Transactional
    void copyDealsToElasticSearch(List<Activity> activities) throws IOException {
        // Set deal sync status

        List<Long> dealIds = activities.stream().map( deal -> deal.getId()).collect(Collectors.toList());

        activityRepository.updateActivitiesStatus(SyncStatus.IN_PROGRESS, dealIds);

        sendRecordsToElasticSearch( activities );

        activityRepository.updateActivitiesStatus(SyncStatus.SYNCED, dealIds);



    }

    private void sendRecordsToElasticSearch(List<Activity> activities) throws IOException {
        BulkRequest request = new BulkRequest();
        for ( Activity activity: activities) {

            ActivitySearchIndexDTO activitySearchIndexDTO = new ActivitySearchIndexDTO(activity);

            request.add(new IndexRequest(SearchConstants.ACTIVITY_INDEX, SearchConstants.ACTIVITY_TYPE,  String.valueOf(activity.getId()))
                .source(activitySearchIndexDTO.getBuilder()));

        }

        BulkResponse response = restHighLevelClient.bulk(request);
    }

}
