package com.leadlet.service.mapper;

import com.leadlet.domain.Team;
import com.leadlet.service.dto.TeamDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    TeamDTO toDto(Team team);

    Team toEntity(TeamDTO teamDTO);

}
