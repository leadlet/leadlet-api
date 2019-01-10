package com.leadlet.service.mapper;

import com.leadlet.domain.DealValue;
import com.leadlet.service.dto.DealValueDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Deal and its DTO DetailedDealDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DealValueMapper extends EntityMapper<DealValueDTO, DealValue> {

    DealValueDTO toDto(DealValue dealValue);

    DealValue toEntity(DealValueDTO dealValueDTO);

}
