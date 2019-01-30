package com.leadlet.service.mapper;

import com.google.common.collect.ImmutableSet;
import com.leadlet.domain.Activity;
import com.leadlet.domain.Deal;
import com.leadlet.domain.Product;
import com.leadlet.service.dto.DealDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity Deal and its DTO DetailedDealDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, UserMapper.class,
    PipelineMapper.class, StageMapper.class, DealValueMapper.class, ProductMapper.class, SourceMapper.class, ChannelMapper.class, ContactMapper.class,LostReasonMapper.class})
public interface DealMapper extends EntityMapper<DealDTO, Deal> {

    @Mappings({
        @Mapping(source = "pipeline.id", target = "pipeline_id"),
        @Mapping(source = "stage.id", target = "stage_id"),
        @Mapping(source = "dealSource.id", target = "deal_source_id"),
        @Mapping(source = "dealChannel.id", target = "deal_channel_id"),
        @Mapping(source = "lostReason.id", target = "lost_reason_id"),
        @Mapping(source = "agent.id", target = "agent_id"),
        @Mapping(source = "contact.id", target = "contact_id"),
        @Mapping(source = "products", target = "product_ids"),
        @Mapping(source = "activities", target = "activity_ids"),
        @Mapping(source = "dealValue", target = "deal_value")
    })
    DealDTO toDto(Deal deal);

    @Mappings({
        @Mapping(source = "pipeline_id", target = "pipeline"),
        @Mapping(source = "stage_id", target = "stage"),
        @Mapping(source = "deal_source_id", target = "dealSource"),
        @Mapping(source = "deal_channel_id", target = "dealChannel"),
        @Mapping(source = "lost_reason_id", target = "lostReason"),
        @Mapping(source = "agent_id", target = "agent"),
        @Mapping(source = "contact_id", target = "contact"),
        @Mapping(source = "product_ids", target = "products"),
        @Mapping(source = "activity_ids", target = "activities"),
        @Mapping(source = "deal_value", target = "dealValue")
    })
    Deal toEntity(DealDTO dealDTO);

    default Deal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deal deal = new Deal();
        deal.setId(id);
        return deal;
    }

    default Set<Product> productIdsToProducts(Set<Long> ids) {
        if(ids==null){
            return ImmutableSet.of();
        }
        return ids.stream().map( id -> {
            Product p = new Product();
            p.setId(id);
            return p;
        }).collect(Collectors.toSet());
    }

    default Set<Long> productsToProductIds(Set<Product> products) {
        if(products==null){
            return ImmutableSet.of();
        }
        return products.stream().map(product -> product.getId()).collect(Collectors.toSet());
    }

    default Set<Activity> activityIdsToActivities(Set<Long> ids) {
        if(ids==null){
            return ImmutableSet.of();
        }

        return ids.stream().map( id -> {
            Activity p = new Activity();
            p.setId(id);
            return p;
        }).collect(Collectors.toSet());
    }

    default Set<Long> activitiesToActivityIds(Set<Activity> activities) {
        if(activities==null){
            return ImmutableSet.of();
        }
        return activities.stream().map(activity -> activity.getId()).collect(Collectors.toSet());
    }

}
