package com.leadlet.service.mapper;

import com.leadlet.domain.Activity;
import com.leadlet.domain.Authority;
import com.leadlet.service.dto.ActivityDTO;
import com.leadlet.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper extends EntityMapper <AuthorityDTO, Authority> {

    AuthorityDTO toDto(Authority authority);

    Authority toEntity(AuthorityDTO authorityDTO);

}
