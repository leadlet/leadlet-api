package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.SubscriptionPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubscriptionPlan and its DTO SubscriptionPlanDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionPlanMapper extends EntityMapper <SubscriptionPlanDTO, SubscriptionPlan> {

    SubscriptionPlan toEntity(SubscriptionPlanDTO subscriptionPlanDTO);
    default SubscriptionPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setId(id);
        return subscriptionPlan;
    }
}
