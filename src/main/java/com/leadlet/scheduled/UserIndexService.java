package com.leadlet.scheduled;

import com.leadlet.config.SearchConstants;
import com.leadlet.domain.User;
import com.leadlet.domain.enumeration.SyncStatus;
import com.leadlet.repository.UserRepository;
import com.leadlet.service.dto.UserSearchIndexDTO;
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
public class UserIndexService {
    private final Logger log = LoggerFactory.getLogger(UserIndexService.class);

    private final RestHighLevelClient restHighLevelClient;
    private final UserRepository userRepository;

    public UserIndexService(RestHighLevelClient restHighLevelClient, UserRepository userRepository) {
        this.restHighLevelClient = restHighLevelClient;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void sync() {

        int pageNo = 0;

        boolean getPages = true;

        while(getPages){
            Pageable pageable = new PageRequest(pageNo, 20);

            Page<User> page = userRepository.findAllBySyncStatusAndCreatedDateLessThan(SyncStatus.NOT_SYNCED, Instant.now(), pageable);

            if( page.getContent() == null ||  page.getContent().size() == 0){
                break;
            }

            try {
                copyDealsToElasticSearch(page.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }


            pageNo++;
        }


    }

    @Transactional
    void copyDealsToElasticSearch(List<User> deals) throws IOException {
        // Set deal sync status

        List<Long> dealIds = deals.stream().map( deal -> deal.getId()).collect(Collectors.toList());

        userRepository.updateUsersStatus(SyncStatus.IN_PROGRESS, dealIds);

        sendRecordsToElasticSearch( deals );

        userRepository.updateUsersStatus(SyncStatus.SYNCED, dealIds);



    }

    private void sendRecordsToElasticSearch(List<User> deals) throws IOException {
        BulkRequest request = new BulkRequest();

        for ( User deal: deals) {
            UserSearchIndexDTO dealSearchIndexDTO = new UserSearchIndexDTO(deal);

            request.add(new IndexRequest(SearchConstants.USER_INDEX, SearchConstants.USER_TYPE,  String.valueOf(deal.getId()))
                .source(dealSearchIndexDTO.getBuilder()));
        }

        BulkResponse response = restHighLevelClient.bulk(request);

        log.info(response.status().toString());
        log.info(response.buildFailureMessage());

    }

}
