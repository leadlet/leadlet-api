package com.leadlet.repository;

import com.leadlet.domain.CompanySubscriptionPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanySubscriptionPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanySubscriptionPlanRepository extends JpaRepository<CompanySubscriptionPlan,Long> {
    
}
