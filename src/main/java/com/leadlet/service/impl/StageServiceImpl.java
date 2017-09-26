package com.leadlet.service.impl;

import com.leadlet.domain.AppAccount;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.StageService;
import com.leadlet.domain.Stage;
import com.leadlet.repository.StageRepository;
import com.leadlet.service.dto.StageDTO;
import com.leadlet.service.mapper.StageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Stage.
 */
@Service
@Transactional
public class StageServiceImpl implements StageService{

    private final Logger log = LoggerFactory.getLogger(StageServiceImpl.class);

    private final StageRepository stageRepository;

    private final StageMapper stageMapper;

    public StageServiceImpl(StageRepository stageRepository, StageMapper stageMapper) {
        this.stageRepository = stageRepository;
        this.stageMapper = stageMapper;
    }

    /**
     * Save a stage.
     *
     * @param stageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StageDTO save(StageDTO stageDTO) {
        log.debug("Request to save Stage : {}", stageDTO);
        Stage stage = stageMapper.toEntity(stageDTO);
        stage.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
        stage = stageRepository.save(stage);
        return stageMapper.toDto(stage);
    }

    /**
     *  Get all the stages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stages");
        return stageRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(stageMapper::toDto);
    }

    /**
     *  Get one stage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StageDTO findOne(Long id) {
        log.debug("Request to get Stage : {}", id);
        Stage stage = stageRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return stageMapper.toDto(stage);
    }

    /**
     *  Delete the  stage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stage : {}", id);
        stageRepository.deleteByIdAndAndAppAccount(id,SecurityUtils.getCurrentUserAppAccount());
    }
}
