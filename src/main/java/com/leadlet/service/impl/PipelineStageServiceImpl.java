package com.leadlet.service.impl;

import com.leadlet.service.PipelineStageService;
import com.leadlet.domain.PipelineStage;
import com.leadlet.repository.PipelineStageRepository;
import com.leadlet.service.dto.PipelineStageDTO;
import com.leadlet.service.mapper.PipelineStageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PipelineStage.
 */
@Service
@Transactional
public class PipelineStageServiceImpl implements PipelineStageService{

    private final Logger log = LoggerFactory.getLogger(PipelineStageServiceImpl.class);

    private final PipelineStageRepository pipelineStageRepository;

    private final PipelineStageMapper pipelineStageMapper;

    public PipelineStageServiceImpl(PipelineStageRepository pipelineStageRepository, PipelineStageMapper pipelineStageMapper) {
        this.pipelineStageRepository = pipelineStageRepository;
        this.pipelineStageMapper = pipelineStageMapper;
    }

    /**
     * Save a pipelineStage.
     *
     * @param pipelineStageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PipelineStageDTO save(PipelineStageDTO pipelineStageDTO) {
        log.debug("Request to save PipelineStage : {}", pipelineStageDTO);
        PipelineStage pipelineStage = pipelineStageMapper.toEntity(pipelineStageDTO);
        pipelineStage = pipelineStageRepository.save(pipelineStage);
        return pipelineStageMapper.toDto(pipelineStage);
    }

    /**
     *  Get all the pipelineStages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PipelineStageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PipelineStages");
        return pipelineStageRepository.findAll(pageable)
            .map(pipelineStageMapper::toDto);
    }

    /**
     *  Get one pipelineStage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PipelineStageDTO findOne(Long id) {
        log.debug("Request to get PipelineStage : {}", id);
        PipelineStage pipelineStage = pipelineStageRepository.findOne(id);
        return pipelineStageMapper.toDto(pipelineStage);
    }

    /**
     *  Delete the  pipelineStage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PipelineStage : {}", id);
        pipelineStageRepository.delete(id);
    }
}
