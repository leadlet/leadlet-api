package com.leadlet.service.impl;

import com.leadlet.service.ObjectiveService;
import com.leadlet.domain.Objective;
import com.leadlet.repository.ObjectiveRepository;
import com.leadlet.service.dto.ObjectiveDTO;
import com.leadlet.service.mapper.ObjectiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Objective.
 */
@Service
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService{

    private final Logger log = LoggerFactory.getLogger(ObjectiveServiceImpl.class);

    private final ObjectiveRepository objectiveRepository;

    private final ObjectiveMapper objectiveMapper;

    public ObjectiveServiceImpl(ObjectiveRepository objectiveRepository, ObjectiveMapper objectiveMapper) {
        this.objectiveRepository = objectiveRepository;
        this.objectiveMapper = objectiveMapper;
    }

    /**
     * Save a objective.
     *
     * @param objectiveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObjectiveDTO save(ObjectiveDTO objectiveDTO) {
        log.debug("Request to save Objective : {}", objectiveDTO);
        Objective objective = objectiveMapper.toEntity(objectiveDTO);
        objective = objectiveRepository.save(objective);
        return objectiveMapper.toDto(objective);
    }

    /**
     *  Get all the objectives.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObjectiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Objectives");
        return objectiveRepository.findAll(pageable)
            .map(objectiveMapper::toDto);
    }

    /**
     *  Get one objective by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ObjectiveDTO findOne(Long id) {
        log.debug("Request to get Objective : {}", id);
        Objective objective = objectiveRepository.findOne(id);
        return objectiveMapper.toDto(objective);
    }

    /**
     *  Delete the  objective by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Objective : {}", id);
        objectiveRepository.delete(id);
    }
}
