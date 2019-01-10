package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.DetailedDealDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Deal and its DTO DetailedDealDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, UserMapper.class,
    PipelineMapper.class, StageMapper.class, DealValueMapper.class, ProductMapper.class, SourceMapper.class, ChannelMapper.class, ContactMapper.class,LostReasonMapper.class})
public interface DetailedDealMapper extends EntityMapper<DetailedDealDTO, Deal> {

    @Mappings({
        @Mapping(source = "dealSource", target = "deal_source"),
        @Mapping(source = "dealChannel", target = "deal_channel"),
        @Mapping(source = "lostReason", target = "lost_reason")
    })
    DetailedDealDTO toDto(Deal deal);

    @Mappings({
        @Mapping(source = "deal_source", target = "dealSource"),
        @Mapping(source = "deal_channel", target = "dealChannel"),
        @Mapping(source = "lost_reason", target = "lostReason"),
        @Mapping(source = "priority", target = "priority", defaultValue = "0")
    })
    Deal toEntity(DetailedDealDTO detailedDealDTO);

    default Deal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deal deal = new Deal();
        deal.setId(id);
        return deal;
    }
}
