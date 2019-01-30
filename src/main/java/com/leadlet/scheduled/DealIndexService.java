package com.leadlet.scheduled;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.Deal;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.dto.DealSearchIndexDTO;
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
            DealSearchIndexDTO dealSearchIndexDTO = new DealSearchIndexDTO(deal);

            request.add(new IndexRequest(SearchConstants.DEAL_INDEX, SearchConstants.DEAL_TYPE,  String.valueOf(deal.getId()))
                .source(dealSearchIndexDTO.getBuilder()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);

        if(response.status().getStatus() != 200 ){
            log.info(response.status().toString());
            log.info(response.buildFailureMessage());
        }

    }

}
