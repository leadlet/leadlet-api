package com.leadlet.service;

import com.leadlet.domain.AppAccount;
import com.leadlet.service.dto.AppAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Service Interface for managing AppAccount.
 */
public interface AppAccountService {

    /**
     * Save a appAccount.
     *
     * @param appAccountDTO the entity to save
     * @return the persisted entity
     */
    AppAccount save(AppAccountDTO appAccountDTO) throws IOException, SQLException;

    /**
     *  Get the "id" appAccount.
     *
     *  @return the entity
     */
    AppAccount getCurrent();

}
