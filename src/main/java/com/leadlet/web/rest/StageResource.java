package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.StageService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.StageDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    private static final String ENTITY_NAME = "stage";

    private final StageService stageService;

    public StageResource(StageService stageService) {
        this.stageService = stageService;
    }

    /**
     * POST  /stages : Create a new stage.
     *
     * @param stageDTO the stageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stageDTO, or with status 400 (Bad Request) if the stage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stages")
    @Timed
    public ResponseEntity<StageDTO> createStage(@RequestBody StageDTO stageDTO) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stageDTO);
        if (stageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stage cannot already have an ID")).body(null);
        }
        StageDTO result = stageService.save(stageDTO);
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stages : Updates an existing stage.
     *
     * @param stageDTO the stageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stageDTO,
     * or with status 400 (Bad Request) if the stageDTO is not valid,
     * or with status 500 (Internal Server Error) if the stageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stages")
    @Timed
    public ResponseEntity<StageDTO> updateStage(@RequestBody StageDTO stageDTO) throws URISyntaxException {
        log.debug("REST request to update Stage : {}", stageDTO);

        StageDTO result = stageService.update(stageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stages : get all the stages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @GetMapping("/stages")
    @Timed
    public ResponseEntity<List<StageDTO>> getStages(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Stages");
        Page<StageDTO> page = stageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stages : get all the stages.
     *
     * @param pipelineId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @GetMapping("/stages/pipeline/{pipelineId}")
    @Timed
    public ResponseEntity<List<StageDTO>> getAllStages(@PathVariable Long pipelineId) {
        log.debug("REST request to get a page of Stages {}", pipelineId);
        List<StageDTO> stageDTOS = stageService.findAllByPipelineId(pipelineId);
        return new ResponseEntity<>(stageDTOS, HttpStatus.OK);
    }

    /**
     * GET  /stages/:id : get the "id" stage.
     *
     * @param id the id of the stageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stages/{id}")
    @Timed
    public ResponseEntity<StageDTO> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        StageDTO stageDTO = stageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stageDTO));
    }

    /**
     * DELETE  /stages/:id : delete the "id" stage.
     *
     * @param id the id of the stageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stages/{id}")
    @Timed
    public ResponseEntity<StageDTO> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageService.delete(id);

        // TODO ygokirmak dirty-fix for null response body

        StageDTO result = new StageDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);

    }
}
