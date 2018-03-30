package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ObjectiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Objective and its DTO ObjectiveDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ObjectiveMapper extends EntityMapper <ObjectiveDTO, Objective> {

    @Mapping(source = "user.id", target = "userId")
    ObjectiveDTO toDto(Objective objective);

    @Mapping(source = "userId", target = "user")
    Objective toEntity(ObjectiveDTO objectiveDTO);

    default Objective fromId(Long id) {
        if (id == null) {
            return null;
        }
        Objective objective = new Objective();
        objective.setId(id);
        return objective;
    }
}
