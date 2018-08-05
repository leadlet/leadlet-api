package com.leadlet.service.mapper;

import com.leadlet.domain.DealChannel;
import com.leadlet.service.dto.ChannelDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Channel and its DTO ChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChannelMapper extends EntityMapper<ChannelDTO, DealChannel> {

    ChannelDTO toDto(DealChannel channel);

    DealChannel toEntity(ChannelDTO channelDTO);

    default DealChannel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DealChannel channel = new DealChannel();
        channel.setId(id);
        return channel;
    }
}
