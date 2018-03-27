package com.leadlet.service.mapper;

import com.leadlet.domain.Team;
import com.leadlet.service.dto.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    TeamDTO toDto(Team team);

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
