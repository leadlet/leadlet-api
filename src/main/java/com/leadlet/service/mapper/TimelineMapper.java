package com.leadlet.service.mapper;

import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactMapper.class, UserMapper.class, DealMapper.class})
public interface TimelineMapper extends EntityMapper<TimelineDTO, Timeline> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "deal.id", target = "dealId")
    TimelineDTO toDto(Timeline timeline);

    @Mapping(source = "contactId", target = "contact")
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
