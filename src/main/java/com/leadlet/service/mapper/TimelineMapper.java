package com.leadlet.service.mapper;

import com.leadlet.domain.Timeline;
import com.leadlet.service.dto.TimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactMapper.class, UserMapper.class})
public interface TimelineMapper extends EntityMapper<TimelineDTO, Timeline> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "user.id", target = "userId")

    TimelineDTO toDto(Timeline timeline);

    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "userId", target = "user")

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
