package com.leadlet.service.impl;

import com.leadlet.domain.Deal;
import com.leadlet.domain.Stage;
import com.leadlet.repository.DealRepository;
import com.leadlet.repository.StageRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.DealService;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.mapper.DealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Deal.
 */
@Service
@Transactional
public class DealServiceImpl implements DealService {

    private final Logger log = LoggerFactory.getLogger(DealServiceImpl.class);

    private final DealRepository dealRepository;

    private final StageRepository stageRepository;

    private final DealMapper dealMapper;

    private final ElasticsearchService elasticsearchService;

    private final TimelineService timelineService;

    public DealServiceImpl(DealRepository dealRepository, DealMapper dealMapper, StageRepository stageRepository,
                           ElasticsearchService elasticsearchService,
                           TimelineService timelineService) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.stageRepository = stageRepository;
        this.elasticsearchService = elasticsearchService;
        this.timelineService = timelineService;
    }

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DealDTO save(DealDTO dealDTO) throws IOException {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        deal = dealRepository.save(deal);
        elasticsearchService.indexDeal(deal);
        timelineService.dealCreated(deal);

        return dealMapper.toDto(deal);
    }

    /**
     * Update a deal.
     *
     * @param dealDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public DealDTO update(DealDTO dealDTO) throws IOException {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(deal.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (dealFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            deal = dealRepository.save(deal);
            elasticsearchService.indexDeal(deal);
            return dealMapper.toDto(deal);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public DealDTO updateStage(Long id, Long stageId) throws IOException {

        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        Stage newStage = stageRepository.findOne(stageId);
        if (dealFromDb != null) {
            dealFromDb.setStage(newStage);
            Deal deal = dealRepository.save(dealFromDb);
            elasticsearchService.indexDeal(deal);
            return dealMapper.toDto(deal);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DealDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealRepository.findByAppAccount_IdOrderByIdAsc(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(dealMapper::toDto);
    }

    /**
     * Get one deal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DealDTO findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        Deal deal = dealRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return dealMapper.toDto(deal);
    }

    /**
     * Delete the  deal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deal : {}", id);
        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (dealFromDb != null) {
            dealRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<DealDTO> query(String searchQuery, Pageable pageable) throws IOException {

        String appAccountFilter = "app_account_id:" + SecurityUtils.getCurrentUserAppAccountId();
        if(StringUtils.isEmpty(searchQuery)){
            searchQuery = appAccountFilter;
        }else {
            searchQuery += " AND " + appAccountFilter;
        }

        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds("leadlet-deal", searchQuery, pageable);

        List<DealDTO> unsorted = dealRepository.findAllByIdIn(response.getFirst()).stream()
            .map(dealMapper::toDto).collect(Collectors.toList());
        List<Long> sortedIds = response.getFirst();

        // we are getting ids from ES sorted but JPA returns result not sorted
        // below code-piece sorts the returned DTOs to have same sort with ids.
        Collections.sort(unsorted,  new Comparator<DealDTO>() {
            public int compare(DealDTO left, DealDTO right) {
                return Integer.compare(sortedIds.indexOf(left.getId()), sortedIds.indexOf(right.getId()));
            }
        } );

        Page<DealDTO> dealDTOS = new PageImpl<DealDTO>(unsorted,
            pageable,
            response.getSecond());

        return dealDTOS;
    }

}
