package com.leadlet.scheduled;

import com.leadlet.domain.Activity;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.ActivityRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
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

    private void sendRecordsToElasticSearch(List<Activity> deals) throws IOException {
        BulkRequest request = new BulkRequest();

        for ( Activity deal: deals) {
            request.add(new IndexRequest("leadlet-activity", "activity", String.valueOf(deal.getId()))
                .source(XContentType.JSON, "id", deal.getId(),
                                            "created_date", new Date(deal.getCreatedDate().toEpochMilli()),
                                            "start_date", new Date(deal.getStart().toEpochMilli()),
                                            "activity_type", deal.getType(),
                                            "title", deal.getTitle(),
                                            "is_done", deal.isDone()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);
    }

}
