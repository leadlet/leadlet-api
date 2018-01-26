package com.leadlet.service.mapper;

import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, OrganizationMapper.class, UserMapper.class, DealMapper.class})
public interface TimelineMapper extends EntityMapper<TimelineDTO, Timeline> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "deal.id", target = "dealId")
    TimelineDTO toDto(Timeline timeline);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "organizationId", target = "organization")
    @Mapping(source = "userId", target = "user")
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
