package com.leadlet.service.mapper;

import com.leadlet.domain.Document;
import com.leadlet.service.dto.DocumentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Document and its DTO DocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, OrganizationMapper.class, DealMapper.class})
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {

    DocumentDTO toDto(Document document);

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
