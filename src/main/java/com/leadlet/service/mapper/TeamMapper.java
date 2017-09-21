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
    TeamDTO toDto(Team team);

    @Mapping(source = "leaderId", target = "leader")
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
