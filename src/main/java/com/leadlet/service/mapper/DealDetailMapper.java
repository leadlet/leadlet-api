package com.leadlet.service.mapper;

import com.leadlet.domain.Deal;
import com.leadlet.service.dto.DealDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, OrganizationMapper.class, UserMapper.class,
    StageMapper.class, PipelineMapper.class, DealValueMapper.class, ProductMapper.class})
public interface DealDetailMapper extends EntityMapper<DealDetailDTO, Deal> {

    @Mapping(source = "stage.id", target = "stageId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "pipeline.id", target = "pipelineId")
    DealDetailDTO toDto(Deal deal);

    Deal toEntity(DealDetailDTO dealDTO);

    default Deal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deal deal = new Deal();
        deal.setId(id);
        return deal;
    }
}
