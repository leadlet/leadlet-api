package com.leadlet.service.impl;

import com.leadlet.domain.DealSource;
import com.leadlet.repository.SourceRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.SourceService;
import com.leadlet.service.dto.SourceDTO;
import com.leadlet.service.mapper.SourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing Source.
 */
@Service
@Transactional
public class SourceServiceImpl implements SourceService {


    private final Logger log = LoggerFactory.getLogger(SourceServiceImpl.class);

    private final SourceRepository sourceRepository;

    private final SourceMapper sourceMapper;

    public SourceServiceImpl(SourceRepository sourceRepository, SourceMapper sourceMapper) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
    }

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceDTO save(SourceDTO sourceDTO) {
        log.debug("Request to save Source : {}", sourceDTO);
        DealSource source = sourceMapper.toEntity(sourceDTO);
        source.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        source = sourceRepository.save(source);
        return sourceMapper.toDto(source);
    }

    /**
     * Update a source.
     *
     * @param sourceDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public SourceDTO update(SourceDTO sourceDTO) {
        log.debug("Request to save Source : {}", sourceDTO);
        DealSource source = sourceMapper.toEntity(sourceDTO);
        DealSource sourceFromDb = sourceRepository.findOneByIdAndAppAccount_Id(source.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (sourceFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            source.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            source = sourceRepository.save(source);
            return sourceMapper.toDto(source);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sources");
        return sourceRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(sourceMapper::toDto);
    }

    /**
     * Get one source by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SourceDTO findOne(Long id) {
        log.debug("Request to get Source : {}", id);
        DealSource source = sourceRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return sourceMapper.toDto(source);
    }

    /**
     * Delete the source by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Source : {}", id);
        DealSource sourceFromDb = sourceRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (sourceFromDb != null) {
            sourceRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
