package com.leadlet.service.impl;

import com.leadlet.domain.User;
import com.leadlet.security.SecurityUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;


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
    public AppAccount save(AppAccountDTO appAccountDTO, MultipartFile gsKeyFile) throws IOException, SQLException {

        if( gsKeyFile != null && gsKeyFile.getSize() > 64000){
            throw new IllegalArgumentException("Key file too big");
        }

        log.debug("Request to save AppAccount : {}", appAccountDTO);
        AppAccount appAccount = appAccountMapper.toEntity(appAccountDTO);

        if( gsKeyFile != null ){
            appAccount.getStoragePreference().setGsKeyFileName(gsKeyFile.getOriginalFilename());
            appAccount.getStoragePreference().setGsKeyFile(new SerialBlob(gsKeyFile.getBytes()));
        }
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
