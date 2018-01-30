package com.leadlet.service.mapper;

import com.leadlet.domain.Location;
import com.leadlet.service.dto.LocationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    LocationDTO toDto(Location location);

    Location toEntity(LocationDTO locationDTO);

}
