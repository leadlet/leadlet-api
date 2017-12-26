package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactPhoneMapper.class})
public interface ContactMapper extends EntityMapper <ContactDTO, Contact> {

    ContactDTO toDto(Contact contact);
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "appAccount", ignore = true)

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
