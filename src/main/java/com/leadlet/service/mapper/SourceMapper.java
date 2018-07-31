package com.leadlet.service.mapper;

import com.leadlet.domain.DealSource;
import com.leadlet.service.dto.SourceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Source and its DTO SourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SourceMapper extends EntityMapper <SourceDTO, DealSource> {

    DealSource toEntity(SourceDTO sourceDTO);
    default DealSource fromId(Long id) {
        if (id == null) {
            return null;
        }
        DealSource source = new DealSource();
        source.setId(id);
        return source;
    }
}
