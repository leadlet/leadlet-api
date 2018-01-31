package com.leadlet.service.mapper;

import com.leadlet.domain.Deal;
import com.leadlet.domain.DealValue;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealValueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DealValueMapper extends EntityMapper<DealValueDTO, DealValue> {

    DealValueDTO toDto(DealValue dealValue);

    DealValue toEntity(DealValueDTO dealValueDTO);

}
