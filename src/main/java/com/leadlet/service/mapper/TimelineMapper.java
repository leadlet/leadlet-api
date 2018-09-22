package com.leadlet.service.mapper;

import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, UserMapper.class, DealMapper.class})
public interface TimelineMapper extends EntityMapper<TimelineDTO, Timeline> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "deal.id", target = "dealId")
    TimelineDTO toDto(Timeline timeline);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "agentId", target = "agent")
    @Mapping(source = "dealId", target = "deal")
    Timeline toEntity(TimelineDTO timelineDTO);

    default Timeline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timeline timeline = new Timeline();
        timeline.setId(id);
        return timeline;
    }
}
