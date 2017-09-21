package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.EmailTemplatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmailTemplates and its DTO EmailTemplatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailTemplatesMapper extends EntityMapper <EmailTemplatesDTO, EmailTemplates> {
    
    
    default EmailTemplates fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailTemplates emailTemplates = new EmailTemplates();
        emailTemplates.setId(id);
        return emailTemplates;
    }
}
