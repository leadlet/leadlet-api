package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.DealDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, OrganizationMapper.class, UserMapper.class, StageMapper.class, DealValueMapper.class})
public interface DealMapper extends EntityMapper<DealDTO, Deal> {

    @Mapping(source = "stage.id", target = "stageId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "stage.pipeline.id", target = "pipelineId")
    DealDTO toDto(Deal deal);

    @Mapping(source = "stageId", target = "stage")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "organizationId", target = "organization")
    @Mapping(source = "ownerId", target = "owner")
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
