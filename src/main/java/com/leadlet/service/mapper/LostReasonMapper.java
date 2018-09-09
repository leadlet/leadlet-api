package com.leadlet.service.mapper;

import com.leadlet.domain.LostReason;
import com.leadlet.service.dto.LostReasonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface LostReasonMapper extends EntityMapper<LostReasonDTO, LostReason> {

    LostReasonDTO toDto(LostReason lostReason);

    LostReason toEntity(LostReasonDTO lostReasonDTO);

    default LostReason fromId(Long id) {
        if (id == null) {
            return null;
        }
        LostReason lostReason = new LostReason();
        lostReason.setId(id);
        return lostReason;
    }
}
