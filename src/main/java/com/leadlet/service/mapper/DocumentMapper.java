package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.DocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Document and its DTO DocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, AppAccountMapper.class, ActivityMapper.class, })
public interface DocumentMapper extends EntityMapper <DocumentDTO, Document> {

    @Mapping(source = "contact.id", target = "contactId")

    @Mapping(source = "appAccount.id", target = "appAccountId")

    @Mapping(source = "activity.id", target = "activityId")
    DocumentDTO toDto(Document document); 

    @Mapping(source = "contactId", target = "contact")

    @Mapping(source = "appAccountId", target = "appAccount")

    @Mapping(source = "activityId", target = "activity")
    Document toEntity(DocumentDTO documentDTO); 
    default Document fromId(Long id) {
        if (id == null) {
            return null;
        }
        Document document = new Document();
        document.setId(id);
        return document;
    }
}
