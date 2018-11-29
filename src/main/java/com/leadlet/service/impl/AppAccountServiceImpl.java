package com.leadlet.service.impl;

import com.leadlet.domain.AppAccount;
import com.leadlet.repository.AppAccountRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.AppAccountService;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;


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
    public AppAccount save(AppAccountDTO appAccountDTO) throws IOException, SQLException {

        log.debug("Request to save AppAccount : {}", appAccountDTO);
        AppAccount appAccount = appAccountMapper.toEntity(appAccountDTO);

        appAccount = appAccountRepository.save(appAccount);
        return appAccount;
    }

    @Override
    public AppAccount getCurrent() {

        Long appAccountId = SecurityUtils.getCurrentUserAppAccountId();

        AppAccount appAccount = appAccountRepository.findOneById(appAccountId);

        return appAccount;
    }

}
