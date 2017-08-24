package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TeamMapper extends EntityMapper <TeamDTO, Team> {

    @Mapping(source = "leader.id", target = "leaderId")

    @Mapping(source = "parent.id", target = "parentId")
    TeamDTO toDto(Team team);

    @Mapping(source = "leaderId", target = "leader")
    @Mapping(target = "users", ignore = true)

    @Mapping(source = "parentId", target = "parent")
    Team toEntity(TeamDTO teamDTO);
    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
