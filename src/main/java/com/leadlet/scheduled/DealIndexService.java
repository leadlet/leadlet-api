package com.leadlet.scheduled;

import com.leadlet.domain.Deal;
import com.leadlet.domain.Product;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.DealRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class DealIndexService {
    private final Logger log = LoggerFactory.getLogger(DealIndexService.class);

    private final RestHighLevelClient restHighLevelClient;
    private final DealRepository dealRepository;

    public DealIndexService(RestHighLevelClient restHighLevelClient, DealRepository dealRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.dealRepository = dealRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void syncDeals() {

        int pageNo = 0;

        boolean getPages = true;

        while(getPages){
            Pageable pageable = new PageRequest(pageNo, 20);

            Page<Deal> dealsPage = dealRepository.findAllBySyncStatusAndCreatedDateLessThan(SyncStatus.NOT_SYNCED, Instant.now(), pageable);

            if( dealsPage.getContent() == null ||  dealsPage.getContent().size() == 0){
                break;
            }

            try {
                copyDealsToElasticSearch(dealsPage.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }


            pageNo++;
        }


    }

    @Transactional
    void copyDealsToElasticSearch(List<Deal> deals) throws IOException {
        // Set deal sync status

        List<Long> dealIds = deals.stream().map( deal -> deal.getId()).collect(Collectors.toList());

        dealRepository.updateDealsStatus(SyncStatus.IN_PROGRESS, dealIds);

        sendRecordsToElasticSearch( deals );

        dealRepository.updateDealsStatus(SyncStatus.SYNCED, dealIds);



    }

    private void sendRecordsToElasticSearch(List<Deal> deals) throws IOException {
        BulkRequest request = new BulkRequest();

        for ( Deal deal: deals) {
            request.add(new IndexRequest("leadlet-deal", "deal",  String.valueOf(deal.getId()))
                .source(XContentType.JSON, "id", deal.getId(),
                                            "created_date", new Date(deal.getCreatedDate().toEpochMilli()),
                                            "pipeline_id", deal.getPipeline().getId(),
                                            "stage_id", deal.getStage().getId(),
                                            "priority", deal.getPriority(),
                                            "source", !StringUtils.isEmpty(deal.getDealSource()) ? deal.getDealSource().getName() : "",
                                            "channel", !StringUtils.isEmpty(deal.getDealChannel()) ? deal.getDealChannel().getName() : "",
                                            "products", deal.getProducts().stream().map(Product::getDescription).toArray()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);

        log.info(response.status().toString());
        log.info(response.buildFailureMessage());

    }

}
