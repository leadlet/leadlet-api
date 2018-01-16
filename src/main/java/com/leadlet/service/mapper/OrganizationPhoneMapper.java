package com.leadlet.service.mapper;

import com.leadlet.domain.OrganizationPhone;
import com.leadlet.service.dto.OrganizationPhoneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity OrganizationPhone and its DTO OrganizationPhoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationPhoneMapper extends EntityMapper<OrganizationPhoneDTO, OrganizationPhone> {

    OrganizationPhoneDTO toDto(OrganizationPhone organizationPhone);

    OrganizationPhone toEntity(OrganizationPhoneDTO organizationPhoneDTO);

    default OrganizationPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganizationPhone organizationPhone = new OrganizationPhone();
        organizationPhone.setId(id);
        return organizationPhone;
    }
}
