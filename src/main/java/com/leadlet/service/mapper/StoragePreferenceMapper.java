package com.leadlet.service.mapper;

import com.leadlet.domain.StoragePreference;
import com.leadlet.service.dto.StoragePreferenceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoragePreferenceMapper extends EntityMapper<StoragePreferenceDTO, StoragePreference> {

    StoragePreferenceDTO toDto(StoragePreference storagePreference);

    StoragePreference toEntity(StoragePreferenceDTO storagePreferenceDTO);

}
