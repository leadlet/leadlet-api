package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.PipelineDTO;

import com.leadlet.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity Pipeline and its DTO PipelineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PipelineMapper extends EntityMapper <PipelineDTO, Pipeline> {

    @Mapping(target = "stages", ignore = true)
    Pipeline toEntity(PipelineDTO pipelineDTO);
    default Pipeline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pipeline pipeline = new Pipeline();
        pipeline.setId(id);
        return pipeline;
    }
}
