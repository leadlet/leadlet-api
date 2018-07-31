package com.leadlet.service.impl;

import com.leadlet.domain.DealChannel;
import com.leadlet.repository.ChannelRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ChannelService;
import com.leadlet.service.dto.ChannelDTO;
import com.leadlet.service.mapper.ChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing Channel.
 */
@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {


    private final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    public ChannelServiceImpl(ChannelRepository channelRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChannelDTO save(ChannelDTO channelDTO) {
        log.debug("Request to save Channel : {}", channelDTO);
        DealChannel dealChannel = channelMapper.toEntity(channelDTO);
        dealChannel.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        dealChannel = channelRepository.save(dealChannel);
        return channelMapper.toDto(dealChannel);
    }

    /**
     * Update a channel.
     *
     * @param channelDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ChannelDTO update(ChannelDTO channelDTO) {
        log.debug("Request to save Channel : {}", channelDTO);
        DealChannel dealChannel = channelMapper.toEntity(channelDTO);
        DealChannel channelFromDb = channelRepository.findOneByIdAndAppAccount_Id(dealChannel.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (channelFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            dealChannel.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            dealChannel = channelRepository.save(dealChannel);
            return channelMapper.toDto(dealChannel);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChannelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Channels");
        return channelRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(channelMapper::toDto);
    }

    /**
     * Get one channel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChannelDTO findOne(Long id) {
        log.debug("Request to get Channel : {}", id);
        DealChannel channel = channelRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return channelMapper.toDto(channel);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Channel : {}", id);
        DealChannel channelFromDb = channelRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (channelFromDb != null) {
            channelRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
