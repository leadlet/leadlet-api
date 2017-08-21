package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactMapper extends EntityMapper <ContactDTO, Contact> {

    @Mapping(source = "organization.id", target = "organizationId")
    ContactDTO toDto(Contact contact); 
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "documents", ignore = true)

    @Mapping(source = "organizationId", target = "organization")
    Contact toEntity(ContactDTO contactDTO); 
    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
