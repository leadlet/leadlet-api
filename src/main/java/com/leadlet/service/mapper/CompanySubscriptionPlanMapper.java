package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.CompanySubscriptionPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompanySubscriptionPlan and its DTO CompanySubscriptionPlanDTO.
 */
@Mapper(componentModel = "spring", uses = {AppAccountMapper.class, SubscriptionPlanMapper.class, })
public interface CompanySubscriptionPlanMapper extends EntityMapper <CompanySubscriptionPlanDTO, CompanySubscriptionPlan> {

    @Mapping(source = "appAccount.id", target = "appAccountId")

    @Mapping(source = "subscriptionPlan.id", target = "subscriptionPlanId")
    CompanySubscriptionPlanDTO toDto(CompanySubscriptionPlan companySubscriptionPlan); 

    @Mapping(source = "appAccountId", target = "appAccount")

    @Mapping(source = "subscriptionPlanId", target = "subscriptionPlan")
    CompanySubscriptionPlan toEntity(CompanySubscriptionPlanDTO companySubscriptionPlanDTO); 
    default CompanySubscriptionPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanySubscriptionPlan companySubscriptionPlan = new CompanySubscriptionPlan();
        companySubscriptionPlan.setId(id);
        return companySubscriptionPlan;
    }
}
