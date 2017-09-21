package com.leadlet.service;

import com.leadlet.service.dto.CompanySubscriptionPlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CompanySubscriptionPlan.
 */
public interface CompanySubscriptionPlanService {

    /**
     * Save a companySubscriptionPlan.
     *
     * @param companySubscriptionPlanDTO the entity to save
     * @return the persisted entity
     */
    CompanySubscriptionPlanDTO save(CompanySubscriptionPlanDTO companySubscriptionPlanDTO);

    /**
     *  Get all the companySubscriptionPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompanySubscriptionPlanDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" companySubscriptionPlan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CompanySubscriptionPlanDTO findOne(Long id);

    /**
     *  Delete the "id" companySubscriptionPlan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
