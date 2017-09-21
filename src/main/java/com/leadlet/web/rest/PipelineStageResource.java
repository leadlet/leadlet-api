package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.PipelineStageService;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.PipelineStageDTO;
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
 * REST controller for managing PipelineStage.
 */
@RestController
@RequestMapping("/api")
public class PipelineStageResource {

    private final Logger log = LoggerFactory.getLogger(PipelineStageResource.class);

    private static final String ENTITY_NAME = "pipelineStage";

    private final PipelineStageService pipelineStageService;

    public PipelineStageResource(PipelineStageService pipelineStageService) {
        this.pipelineStageService = pipelineStageService;
    }

    /**
     * POST  /pipeline-stages : Create a new pipelineStage.
     *
     * @param pipelineStageDTO the pipelineStageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pipelineStageDTO, or with status 400 (Bad Request) if the pipelineStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pipeline-stages")
    @Timed
    public ResponseEntity<PipelineStageDTO> createPipelineStage(@RequestBody PipelineStageDTO pipelineStageDTO) throws URISyntaxException {
        log.debug("REST request to save PipelineStage : {}", pipelineStageDTO);
        if (pipelineStageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pipelineStage cannot already have an ID")).body(null);
        }
        PipelineStageDTO result = pipelineStageService.save(pipelineStageDTO);
        return ResponseEntity.created(new URI("/api/pipeline-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pipeline-stages : Updates an existing pipelineStage.
     *
     * @param pipelineStageDTO the pipelineStageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pipelineStageDTO,
     * or with status 400 (Bad Request) if the pipelineStageDTO is not valid,
     * or with status 500 (Internal Server Error) if the pipelineStageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pipeline-stages")
    @Timed
    public ResponseEntity<PipelineStageDTO> updatePipelineStage(@RequestBody PipelineStageDTO pipelineStageDTO) throws URISyntaxException {
        log.debug("REST request to update PipelineStage : {}", pipelineStageDTO);
        if (pipelineStageDTO.getId() == null) {
            return createPipelineStage(pipelineStageDTO);
        }
        PipelineStageDTO result = pipelineStageService.save(pipelineStageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pipelineStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pipeline-stages : get all the pipelineStages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pipelineStages in body
     */
    @GetMapping("/pipeline-stages")
    @Timed
    public ResponseEntity<List<PipelineStageDTO>> getAllPipelineStages(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PipelineStages");
        Page<PipelineStageDTO> page = pipelineStageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pipeline-stages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pipeline-stages/:id : get the "id" pipelineStage.
     *
     * @param id the id of the pipelineStageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pipelineStageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pipeline-stages/{id}")
    @Timed
    public ResponseEntity<PipelineStageDTO> getPipelineStage(@PathVariable Long id) {
        log.debug("REST request to get PipelineStage : {}", id);
        PipelineStageDTO pipelineStageDTO = pipelineStageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pipelineStageDTO));
    }

    /**
     * DELETE  /pipeline-stages/:id : delete the "id" pipelineStage.
     *
     * @param id the id of the pipelineStageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pipeline-stages/{id}")
    @Timed
    public ResponseEntity<Void> deletePipelineStage(@PathVariable Long id) {
        log.debug("REST request to delete PipelineStage : {}", id);
        pipelineStageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
