package com.leadlet.service.impl;

import com.leadlet.domain.AppAccount;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.PipelineService;
import com.leadlet.domain.Pipeline;
import com.leadlet.repository.PipelineRepository;
import com.leadlet.service.dto.PipelineDTO;
import com.leadlet.service.mapper.PipelineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Pipeline.
 */
@Service
@Transactional
public class PipelineServiceImpl implements PipelineService{

    private final Logger log = LoggerFactory.getLogger(PipelineServiceImpl.class);

    private final PipelineRepository pipelineRepository;

    private final PipelineMapper pipelineMapper;

    public PipelineServiceImpl(PipelineRepository pipelineRepository, PipelineMapper pipelineMapper) {
        this.pipelineRepository = pipelineRepository;
        this.pipelineMapper = pipelineMapper;
    }

    /**
     * Save a pipeline.
     *
     * @param pipelineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PipelineDTO save(PipelineDTO pipelineDTO) {
        log.debug("Request to save Pipeline : {}", pipelineDTO);
        Pipeline pipeline = pipelineMapper.toEntity(pipelineDTO);
        pipeline.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
        pipeline = pipelineRepository.save(pipeline);
        return pipelineMapper.toDto(pipeline);
    }

    /**
     *  Get all the pipelines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PipelineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pipelines");
        return pipelineRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(pipelineMapper::toDto);
    }

    /**
     *  Get one pipeline by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PipelineDTO findOne(Long id) {
        log.debug("Request to get Pipeline : {}", id);
        Pipeline pipeline = pipelineRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return pipelineMapper.toDto(pipeline);
    }

    /**
     *  Delete the  pipeline by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pipeline : {}", id);
        pipelineRepository.deleteByIdAndAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
    }
}
