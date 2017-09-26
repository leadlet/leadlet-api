package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {DealMapper.class, ContactMapper.class, UserMapper.class, })
public interface ActivityMapper extends EntityMapper <ActivityDTO, Activity> {

    @Mapping(source = "deal.id", target = "dealId")

    @Mapping(source = "person.id", target = "personId")

    @Mapping(source = "organization.id", target = "organizationId")

    @Mapping(source = "user.id", target = "userId")
    ActivityDTO toDto(Activity activity);
    @Mapping(target = "documents", ignore = true)

    @Mapping(source = "dealId", target = "deal")

    @Mapping(source = "personId", target = "person")

    @Mapping(source = "organizationId", target = "organization")

    @Mapping(source = "userId", target = "user")
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
