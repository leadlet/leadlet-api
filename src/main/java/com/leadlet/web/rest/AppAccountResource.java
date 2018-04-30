package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.domain.AppAccount;
import com.leadlet.service.AppAccountService;
import com.leadlet.service.MailService;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import com.leadlet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AppAccountResource {

    private final static String ENTITY_NAME = "Account";

    private final Logger log = LoggerFactory.getLogger(AppAccountResource.class);

    private final AppAccountService appAccountService;

    private final MailService mailService;

    private final AppAccountMapper appAccountMapper;

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    public AppAccountResource( AppAccountService appAccountService,
                              MailService mailService, AppAccountMapper appAccountMapper) {

        this.appAccountService = appAccountService;
        this.mailService = mailService;
        this.appAccountMapper = appAccountMapper;

    }

    /**
     * GET  /account : get the current app account.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public ResponseEntity<AppAccountDTO> getAccount() {

        AppAccount appAccount = appAccountService.getCurrent();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appAccountMapper.toDto(appAccount)));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param appAccountDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping(value = "/account",consumes = {"multipart/form-data"})
    @Timed
    public ResponseEntity<AppAccountDTO> updateAccount(@RequestPart(value = "gsKeyFile", required = false) MultipartFile gsKeyFile, @RequestPart(value = "account") AppAccountDTO appAccountDTO) throws URISyntaxException, IOException, SQLException {
        log.debug("REST request to update AppAccount : {}", appAccountDTO);

        AppAccount result = appAccountService.save(appAccountDTO, gsKeyFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(appAccountMapper.toDto(result));
    }
}
