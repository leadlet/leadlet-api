package com.leadlet.service;

import com.leadlet.service.dto.SubscriptionPlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SubscriptionPlan.
 */
public interface SubscriptionPlanService {

    /**
     * Save a subscriptionPlan.
     *
     * @param subscriptionPlanDTO the entity to save
     * @return the persisted entity
     */
    SubscriptionPlanDTO save(SubscriptionPlanDTO subscriptionPlanDTO);

    /**
     *  Get all the subscriptionPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubscriptionPlanDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" subscriptionPlan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SubscriptionPlanDTO findOne(Long id);

    /**
     *  Delete the "id" subscriptionPlan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
