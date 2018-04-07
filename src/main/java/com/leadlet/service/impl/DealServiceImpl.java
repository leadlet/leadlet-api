package com.leadlet.service.impl;

import com.leadlet.domain.Organization;
import com.leadlet.domain.Stage;
import com.leadlet.repository.StageRepository;
import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.repository.util.SpecificationsBuilder;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.DealService;
import com.leadlet.domain.Deal;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.StageService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealDetailDTO;
import com.leadlet.service.dto.DealMoveDTO;
import com.leadlet.service.mapper.DealDetailMapper;
import com.leadlet.service.mapper.DealMapper;
import com.leadlet.web.rest.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
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

    public DealServiceImpl(DealRepository dealRepository, DealMapper dealMapper, StageRepository stageRepository,
                           DealDetailMapper dealDetailMapper) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.stageRepository = stageRepository;
        this.dealDetailMapper = dealDetailMapper;
    }

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DealDetailDTO save(DealDTO dealDTO) {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        deal = dealRepository.save(deal);
        return dealDetailMapper.toDto(deal);
    }

    /**
     * Update a deal.
     *
     * @param dealDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public DealDetailDTO update(DealDTO dealDTO) {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        Deal dealFromDb = dealRepository.findOneByIdAndAppAccount_Id(deal.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (dealFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            deal.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            deal = dealRepository.save(deal);
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
    public DealDetailDTO move(DealMoveDTO dealMoveDTO) {
        Deal prevDeal = null;
        Deal nextDeal = null;

        if (dealMoveDTO.getPrevDealId() != null) {
            prevDeal = dealRepository.getOne(dealMoveDTO.getPrevDealId());
        }

        if (dealMoveDTO.getNextDealId() != null) {
            nextDeal = dealRepository.getOne(dealMoveDTO.getNextDealId());
        }
        Integer newPriority = -1;

        if (prevDeal == null && nextDeal != null) {
            newPriority = nextDeal.getPriority() - 1;
        } else if (prevDeal != null && nextDeal != null) {
            if ((nextDeal.getPriority() - prevDeal.getPriority()) >= 2) {
                // we have room for new deal
                newPriority = nextDeal.getPriority() - 1;
            } else {
                newPriority = nextDeal.getPriority();
                dealRepository.shiftDealsUp(SecurityUtils.getCurrentUserAppAccountId(), nextDeal.getStage().getId(), newPriority);
            }
        } else if (nextDeal == null && prevDeal != null) {
            newPriority = prevDeal.getPriority() + 1;
        } else {
            newPriority = 0;
        }

        Deal movingDeal = dealRepository.getOne(dealMoveDTO.getId());
        movingDeal.setPriority(newPriority);

        Stage newStage = stageRepository.findOne(dealMoveDTO.getNewStageId());

        movingDeal.setStage(newStage);

        movingDeal = dealRepository.save(movingDeal);
        return dealDetailMapper.toDto(movingDeal);
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
    public Page<DealDetailDTO> findAllByOrganizationId(Long organizationId, Pageable page) {
        Page<DealDetailDTO> dealList = dealRepository.findAllByAppAccount_IdAndOrganization_IdOrderByPriorityAsc(SecurityUtils.getCurrentUserAppAccountId(), organizationId, page).map(dealDetailMapper::toDto);

        return dealList;
    }

    @Override
    public Double getDealTotalByStage(Long stageId) {
        return dealRepository.calculateDealTotalByStageId(SecurityUtils.getCurrentUserAppAccountId(),stageId);
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
