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
     *  Get all the appAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AppAccountDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" appAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AppAccountDTO findOne(Long id);

    /**
     *  Delete the "id" appAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
