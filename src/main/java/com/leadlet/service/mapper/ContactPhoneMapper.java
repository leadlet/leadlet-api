package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.ContactPhoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactPhone and its DTO ContactPhoneDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, })
public interface ContactPhoneMapper extends EntityMapper <ContactPhoneDTO, ContactPhone> {

    @Mapping(source = "contact.id", target = "contactId")
    ContactPhoneDTO toDto(ContactPhone contactPhone); 

    @Mapping(source = "contactId", target = "contact")
    ContactPhone toEntity(ContactPhoneDTO contactPhoneDTO); 
    default ContactPhone fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactPhone contactPhone = new ContactPhone();
        contactPhone.setId(id);
        return contactPhone;
    }
}
