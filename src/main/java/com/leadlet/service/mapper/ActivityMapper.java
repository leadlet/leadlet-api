package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {DetailedDealMapper.class, ContactMapper.class, UserMapper.class,  LocationMapper.class, ActivityTypeMapper.class})
public interface ActivityMapper extends EntityMapper <ActivityDTO, Activity> {

    ActivityDTO toDto(Activity activity);

    Activity toEntity(ActivityDTO activityDTO);
    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
