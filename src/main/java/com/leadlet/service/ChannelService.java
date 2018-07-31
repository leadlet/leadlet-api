package com.leadlet.service;

import com.leadlet.service.dto.ChannelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChannelService {

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save
     * @return the persisted entity
     */
    ChannelDTO save(ChannelDTO channelDTO);

    /**
     * Update a channel.
     *
     * @param channelDTO the entity to update
     * @return the persisted entity
     */
    ChannelDTO update(ChannelDTO channelDTO);

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ChannelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" channel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ChannelDTO findOne(Long id);

    /**
     * Delete the "id" channel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
