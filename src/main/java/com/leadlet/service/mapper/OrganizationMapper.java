package com.leadlet.service.mapper;

import com.leadlet.domain.Organization;
import com.leadlet.service.dto.OrganizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationPhoneMapper.class})
public interface OrganizationMapper extends EntityMapper <OrganizationDTO, Organization> {

    OrganizationDTO toDto(Organization organization);
    @Mapping(target = "appAccount", ignore = true)

    Organization toEntity(OrganizationDTO contactDTO);
    default Organization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
