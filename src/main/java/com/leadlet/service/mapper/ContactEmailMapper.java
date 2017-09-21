package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ContactEmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactEmail and its DTO ContactEmailDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, })
public interface ContactEmailMapper extends EntityMapper <ContactEmailDTO, ContactEmail> {

    @Mapping(source = "contact.id", target = "contactId")
    ContactEmailDTO toDto(ContactEmail contactEmail); 

    @Mapping(source = "contactId", target = "contact")
    ContactEmail toEntity(ContactEmailDTO contactEmailDTO); 
    default ContactEmail fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactEmail contactEmail = new ContactEmail();
        contactEmail.setId(id);
        return contactEmail;
    }
}
