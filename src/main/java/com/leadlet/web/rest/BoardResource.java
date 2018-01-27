package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.DealService;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.DealMoveDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.web.rest.vm.BoardVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Deal.
 */
@RestController
@RequestMapping("/api")
public class BoardResource {

    private final Logger log = LoggerFactory.getLogger(BoardResource.class);

    private static final String ENTITY_NAME = "board";

    private final BoardService boardService;

    public BoardResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards/{pipelineId}")
    @Timed
    public ResponseEntity<BoardVM> getDeal(@PathVariable Long pipelineId) {
        log.debug("REST request to get Deal : {}", id);
        DealDTO dealDTO = dealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealDTO));
    }

}
