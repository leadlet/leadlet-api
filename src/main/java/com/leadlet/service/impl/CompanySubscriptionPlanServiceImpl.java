package com.leadlet.service.impl;

import com.leadlet.service.CompanySubscriptionPlanService;
import com.leadlet.domain.CompanySubscriptionPlan;
import com.leadlet.repository.CompanySubscriptionPlanRepository;
import com.leadlet.service.dto.CompanySubscriptionPlanDTO;
import com.leadlet.service.mapper.CompanySubscriptionPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CompanySubscriptionPlan.
 */
@Service
@Transactional
public class CompanySubscriptionPlanServiceImpl implements CompanySubscriptionPlanService{

    private final Logger log = LoggerFactory.getLogger(CompanySubscriptionPlanServiceImpl.class);

    private final CompanySubscriptionPlanRepository companySubscriptionPlanRepository;

    private final CompanySubscriptionPlanMapper companySubscriptionPlanMapper;

    public CompanySubscriptionPlanServiceImpl(CompanySubscriptionPlanRepository companySubscriptionPlanRepository, CompanySubscriptionPlanMapper companySubscriptionPlanMapper) {
        this.companySubscriptionPlanRepository = companySubscriptionPlanRepository;
        this.companySubscriptionPlanMapper = companySubscriptionPlanMapper;
    }

    /**
     * Save a companySubscriptionPlan.
     *
     * @param companySubscriptionPlanDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanySubscriptionPlanDTO save(CompanySubscriptionPlanDTO companySubscriptionPlanDTO) {
        log.debug("Request to save CompanySubscriptionPlan : {}", companySubscriptionPlanDTO);
        CompanySubscriptionPlan companySubscriptionPlan = companySubscriptionPlanMapper.toEntity(companySubscriptionPlanDTO);
        companySubscriptionPlan = companySubscriptionPlanRepository.save(companySubscriptionPlan);
        return companySubscriptionPlanMapper.toDto(companySubscriptionPlan);
    }

    /**
     *  Get all the companySubscriptionPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanySubscriptionPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanySubscriptionPlans");
        return companySubscriptionPlanRepository.findAll(pageable)
            .map(companySubscriptionPlanMapper::toDto);
    }

    /**
     *  Get one companySubscriptionPlan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanySubscriptionPlanDTO findOne(Long id) {
        log.debug("Request to get CompanySubscriptionPlan : {}", id);
        CompanySubscriptionPlan companySubscriptionPlan = companySubscriptionPlanRepository.findOne(id);
        return companySubscriptionPlanMapper.toDto(companySubscriptionPlan);
    }

    /**
     *  Delete the  companySubscriptionPlan by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanySubscriptionPlan : {}", id);
        companySubscriptionPlanRepository.delete(id);
    }
}
