package com.leadlet.service.impl;

import com.leadlet.service.DealService;
import com.leadlet.domain.Deal;
import com.leadlet.repository.DealRepository;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.mapper.DealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Deal.
 */
@Service
@Transactional
public class DealServiceImpl implements DealService{

    private final Logger log = LoggerFactory.getLogger(DealServiceImpl.class);

    private final DealRepository dealRepository;

    private final DealMapper dealMapper;

    public DealServiceImpl(DealRepository dealRepository, DealMapper dealMapper) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
    }

    /**
     * Save a deal.
     *
     * @param dealDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DealDTO save(DealDTO dealDTO) {
        log.debug("Request to save Deal : {}", dealDTO);
        Deal deal = dealMapper.toEntity(dealDTO);
        deal = dealRepository.save(deal);
        return dealMapper.toDto(deal);
    }

    /**
     *  Get all the deals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DealDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealRepository.findAll(pageable)
            .map(dealMapper::toDto);
    }

    /**
     *  Get one deal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DealDTO findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        Deal deal = dealRepository.findOne(id);
        return dealMapper.toDto(deal);
    }

    /**
     *  Delete the  deal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deal : {}", id);
        dealRepository.delete(id);
    }
}
