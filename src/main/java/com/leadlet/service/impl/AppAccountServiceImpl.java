package com.leadlet.service.impl;

import com.leadlet.service.AppAccountService;
import com.leadlet.domain.AppAccount;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AppAccount.
 */
@Service
@Transactional
public class AppAccountServiceImpl implements AppAccountService{

    private final Logger log = LoggerFactory.getLogger(AppAccountServiceImpl.class);

    private final AppAccountRepository appAccountRepository;

    private final AppAccountMapper appAccountMapper;

    public AppAccountServiceImpl(AppAccountRepository appAccountRepository, AppAccountMapper appAccountMapper) {
        this.appAccountRepository = appAccountRepository;
        this.appAccountMapper = appAccountMapper;
    }

    /**
     * Save a appAccount.
     *
     * @param appAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AppAccountDTO save(AppAccountDTO appAccountDTO) {
        log.debug("Request to save AppAccount : {}", appAccountDTO);
        AppAccount appAccount = appAccountMapper.toEntity(appAccountDTO);
        appAccount = appAccountRepository.save(appAccount);
        return appAccountMapper.toDto(appAccount);
    }

    /**
     *  Get all the appAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppAccounts");
        return appAccountRepository.findAll(pageable)
            .map(appAccountMapper::toDto);
    }

    /**
     *  Get one appAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AppAccountDTO findOne(Long id) {
        log.debug("Request to get AppAccount : {}", id);
        AppAccount appAccount = appAccountRepository.findOne(id);
        return appAccountMapper.toDto(appAccount);
    }

    /**
     *  Delete the  appAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppAccount : {}", id);
        appAccountRepository.delete(id);
    }
}
