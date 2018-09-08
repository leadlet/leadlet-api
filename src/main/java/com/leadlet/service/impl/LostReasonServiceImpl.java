package com.leadlet.service.impl;

import com.leadlet.domain.LostReason;
import com.leadlet.repository.LostReasonRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.LostReasonService;
import com.leadlet.service.dto.LostReasonDTO;
import com.leadlet.service.mapper.LostReasonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing Lost Reason.
 */
@Service
@Transactional
public class LostReasonServiceImpl implements LostReasonService {


    private final Logger log = LoggerFactory.getLogger(LostReasonServiceImpl.class);

    private final LostReasonRepository lostReasonRepository;

    private final LostReasonMapper lostReasonMapper;


    public LostReasonServiceImpl(LostReasonRepository lostReasonRepository, LostReasonMapper lostReasonMapper) {
        this.lostReasonRepository = lostReasonRepository;
        this.lostReasonMapper = lostReasonMapper;
    }

    /**
     * Save a lost reason.
     *
     * @param lostReasonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LostReasonDTO save(LostReasonDTO lostReasonDTO) {
        log.debug("Request to save Lost Reason : {}", lostReasonDTO);
        LostReason lostReason = lostReasonMapper.toEntity(lostReasonDTO);
        lostReason.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        lostReason = lostReasonRepository.save(lostReason);
        return lostReasonMapper.toDto(lostReason);
    }

    /**
     * Update a lost reason.
     *
     * @param lostReasonDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public LostReasonDTO update(LostReasonDTO lostReasonDTO) {
        log.debug("Request to save Lost Reason : {}", lostReasonDTO);
        LostReason lostReason = lostReasonMapper.toEntity(lostReasonDTO);
        LostReason lostReasonFromDb = lostReasonRepository.findOneByIdAndAppAccount_Id(lostReason.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (lostReasonFromDb != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            lostReason.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            lostReason = lostReasonRepository.save(lostReason);
            return lostReasonMapper.toDto(lostReason);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the lost reasons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LostReasonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lost Reasons");
        return lostReasonRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(lostReasonMapper::toDto);
    }

    /**
     * Get one lost reason by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LostReasonDTO findOne(Long id) {
        log.debug("Request to get Lost Reason : {}", id);
        LostReason lostReason = lostReasonRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return lostReasonMapper.toDto(lostReason);
    }

    /**
     * Delete the lost reason by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lost Reason : {}", id);
        LostReason lostReasonFromDb = lostReasonRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (lostReasonFromDb != null) {
            lostReasonRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
