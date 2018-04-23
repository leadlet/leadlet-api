package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.domain.User;
import com.leadlet.repository.UserRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.AppAccountService;
import com.leadlet.service.MailService;
import com.leadlet.service.UserService;
import com.leadlet.service.dto.AppAccountDTO;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealDetailDTO;
import com.leadlet.service.dto.UserDTO;
import com.leadlet.service.mapper.AppAccountMapper;
import com.leadlet.service.mapper.UserMapper;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.vm.KeyAndPasswordVM;
import com.leadlet.web.rest.vm.ManagedUserVM;
import com.leadlet.web.rest.vm.RegisterUserVm;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
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

        AppAccountDTO appAccountDTO = appAccountService.getCurrent();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appAccountDTO));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param appAccountDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    public ResponseEntity<AppAccountDTO> updateAccount(@RequestBody AppAccountDTO appAccountDTO) throws URISyntaxException {
        log.debug("REST request to update AppAccount : {}", appAccountDTO);

        AppAccountDTO result = appAccountService.save(appAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
