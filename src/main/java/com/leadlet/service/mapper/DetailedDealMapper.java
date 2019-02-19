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
        @Mapping(source = "dealStatus", target = "deal_status"),
        @Mapping(source = "dealSource", target = "deal_source"),
        @Mapping(source = "dealChannel", target = "deal_channel"),
        @Mapping(source = "dealValue", target = "deal_value"),
        @Mapping(source = "lostReason", target = "lost_reason"),
        @Mapping(source = "possibleCloseDate", target = "possible_close_date"),
        @Mapping(source = "createdDate", target = "created_date"),
        @Mapping(source = "lastModifiedDate", target = "last_modified_date")
    })
    DetailedDealDTO toDto(Deal deal);

    @Mappings({
        @Mapping(source = "deal_status", target = "dealStatus"),
        @Mapping(source = "deal_source", target = "dealSource"),
        @Mapping(source = "deal_channel", target = "dealChannel"),
        @Mapping(source = "lost_reason", target = "lostReason"),
        @Mapping(source = "deal_value", target = "dealValue"),
        @Mapping(source = "possible_close_date", target = "possibleCloseDate"),
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
