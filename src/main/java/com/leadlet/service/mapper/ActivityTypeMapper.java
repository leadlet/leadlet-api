package com.leadlet.service.mapper;

import com.leadlet.domain.ActivityType;
import com.leadlet.service.dto.ActivityTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ActivityTypeMapper extends EntityMapper<ActivityTypeDTO, ActivityType> {

    ActivityTypeDTO toDto(ActivityType activityType);

    ActivityType toEntity(ActivityTypeDTO activityTypeDTO);

    default ActivityType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityType activityType = new ActivityType();
        activityType.setId(id);
        return activityType;
    }
}
