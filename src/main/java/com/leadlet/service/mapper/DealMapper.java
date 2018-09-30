package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.DealDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, UserMapper.class,
    PipelineMapper.class, StageMapper.class, DealValueMapper.class, ProductMapper.class, SourceMapper.class, ChannelMapper.class, PersonMapper.class,LostReasonMapper.class})
public interface DealMapper extends EntityMapper<DealDTO, Deal> {

    DealDTO toDto(Deal deal);

    @Mapping(source = "priority", target = "priority", defaultValue = "0")
    Deal toEntity(DealDTO dealDTO);

    default Deal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deal deal = new Deal();
        deal.setId(id);
        return deal;
    }
}
