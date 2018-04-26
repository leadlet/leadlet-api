package com.leadlet.service.mapper;

import com.leadlet.domain.StoragePreference;
import com.leadlet.domain.Team;
import com.leadlet.service.dto.StoragePreferenceDTO;
import com.leadlet.service.dto.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoragePreferenceMapper extends EntityMapper<StoragePreferenceDTO, StoragePreference> {

    @Mapping(target = "gsKeyFile", ignore = true)
    StoragePreferenceDTO toDto(StoragePreference storagePreference);

    @Mapping(target = "gsKeyFile", ignore = true)
    StoragePreference toEntity(StoragePreferenceDTO storagePreferenceDTO);

    default StoragePreference fromId(Long id) {
        if (id == null) {
            return null;
        }
        StoragePreference storagePreference = new StoragePreference();
        storagePreference.setId(id);
        return storagePreference;
    }
}
