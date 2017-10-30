package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.StageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stage and its DTO StageDTO.
 */
@Mapper(componentModel = "spring", uses = {PipelineMapper.class})
public interface StageMapper extends EntityMapper <StageDTO, Stage> {

    @Mapping(source = "pipeline.id", target = "pipelineId")
    StageDTO toDto(Stage stage);

    @Mapping(source = "pipelineId", target = "pipeline")
    Stage toEntity(StageDTO stageDTO);
    default Stage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stage stage = new Stage();
        stage.setId(id);
        return stage;
    }
}
