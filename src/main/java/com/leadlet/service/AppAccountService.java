package com.leadlet.service;

import com.leadlet.service.dto.AppAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    AppAccountDTO save(AppAccountDTO appAccountDTO);

    /**
     *  Get the "id" appAccount.
     *
     *  @return the entity
     */
    AppAccountDTO getCurrent();

}
