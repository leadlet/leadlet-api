package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.PipelineStageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PipelineStage and its DTO PipelineStageDTO.
 */
@Mapper(componentModel = "spring", uses = {PipelineMapper.class, StageMapper.class, })
public interface PipelineStageMapper extends EntityMapper <PipelineStageDTO, PipelineStage> {

    @Mapping(source = "pipeline.id", target = "pipelineId")

    @Mapping(source = "stage.id", target = "stageId")
    PipelineStageDTO toDto(PipelineStage pipelineStage); 

    @Mapping(source = "pipelineId", target = "pipeline")

    @Mapping(source = "stageId", target = "stage")
    PipelineStage toEntity(PipelineStageDTO pipelineStageDTO); 
    default PipelineStage fromId(Long id) {
        if (id == null) {
            return null;
        }
        PipelineStage pipelineStage = new PipelineStage();
        pipelineStage.setId(id);
        return pipelineStage;
    }
}
