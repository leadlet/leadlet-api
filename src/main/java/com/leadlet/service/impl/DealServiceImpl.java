package com.leadlet.service.impl;

import com.leadlet.domain.Deal;
import com.leadlet.domain.Stage;
import com.leadlet.repository.DealRepository;
import com.leadlet.repository.StageRepository;
import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.repository.util.SpecificationsBuilder;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.DealService;
import com.leadlet.service.ElasticsearchService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealDetailDTO;
import com.leadlet.service.mapper.DealDetailMapper;
import com.leadlet.service.mapper.DealMapper;
import com.leadlet.web.rest.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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

    private final DealDetailMapper dealDetailMapper;

    private final ElasticsearchService elasticsearchService;

    public DealServiceImpl(DealRepository dealRepository, DealMapper dealMapper, StageRepository stageRepository,
                           DealDetailMapper dealDetailMapper, ElasticsearchService elasticsearchService) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.stageRepository = stageRepository;
        this.dealDetailMapper = dealDetailMapper;
        this.elasticsearchService = elasticsearchService;
    }

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DealDetailDTO save(DealDTO dealDTO) throws IOException {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        deal = dealRepository.save(deal);
       // elasticsearchService.indexDeal(deal);
        return dealDetailMapper.toDto(deal);
    }

    /**
     * Update a deal.
     *
     * @param dealDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public DealDetailDTO update(DealDTO dealDTO) throws IOException {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(deal.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (dealFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            deal = dealRepository.save(deal);
            elasticsearchService.indexDeal(deal);
            return dealDetailMapper.toDto(deal);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public DealDetailDTO patch(Long id, Integer priority, Long stageId) throws IOException {

        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        Stage newStage = stageRepository.findOne(stageId);
        if (dealFromDb != null) {
            dealFromDb.setPriority(priority);
            dealFromDb.setStage(newStage);
            Deal deal = dealRepository.save(dealFromDb);
            elasticsearchService.indexDeal(deal);
            return dealDetailMapper.toDto(deal);
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
    public DealDetailDTO findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        Deal deal = dealRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return dealDetailMapper.toDto(deal);
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
    public Page<DealDTO> findAllByStageId(Long stageId, Pageable page) {
        Page<DealDTO> dealList = dealRepository.findAllByAppAccount_IdAndStage_IdOrderByPriorityAsc(SecurityUtils.getCurrentUserAppAccountId(), stageId, page).map(dealMapper::toDto);

        return dealList;
    }

    @Override
    public Page<DealDetailDTO> findAllByPersonId(Long personId, Pageable page) {
        Page<DealDetailDTO> dealList = dealRepository.findAllByAppAccount_IdAndPerson_IdOrderByPriorityAsc(SecurityUtils.getCurrentUserAppAccountId(), personId, page).map(dealDetailMapper::toDto);

        return dealList;
    }

    @Override
    public Double getDealTotalByStage(Long stageId) {
        return dealRepository.calculateDealTotalByStageId(SecurityUtils.getCurrentUserAppAccountId(),stageId);
    }

    @Override
    public Page<DealDTO> query(String searchQuery, Pageable pageable) throws IOException {

        Pair<List<Long>, Long> response = elasticsearchService.getEntityIds("leadlet-deal", searchQuery, pageable);

        Page<DealDTO> deals = new PageImpl<DealDTO>(dealRepository.findAllByIdIn(response.getFirst()).stream()
            .map(dealMapper::toDto).collect(Collectors.toList()),
            pageable,
            response.getSecond());

        return deals;
    }

    @Override
    public Page<DealDTO> search(String filter, Pageable pageable) {
        log.debug("Request to get all Deals");
        SpecificationsBuilder builder = new SpecificationsBuilder();

        if (!StringUtils.isEmpty(filter)) {
            List<SearchCriteria> criteriaList = ParameterUtil.createCriterias(filter);

            for (SearchCriteria criteria : criteriaList) {
                builder.with(criteria);
            }

        }

        builder.with("appAccount", ":", SecurityUtils.getCurrentUserAppAccountReference());

        Specification<Deal> spec = builder.build();

        return dealRepository.findAll(spec, pageable)
            .map(dealMapper::toDto);
    }

}
