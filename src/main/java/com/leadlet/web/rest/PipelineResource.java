package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.domain.AppAccount;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.PipelineService;
import com.leadlet.service.dto.StageDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.PipelineDTO;
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
 * REST controller for managing Pipeline.
 */
@RestController
@RequestMapping("/api")
public class PipelineResource {

    private final Logger log = LoggerFactory.getLogger(PipelineResource.class);

    private static final String ENTITY_NAME = "pipeline";

    private final PipelineService pipelineService;

    public PipelineResource(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    /**
     * POST  /pipelines : Create a new pipeline.
     *
     * @param pipelineDTO the pipelineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pipelineDTO, or with status 400 (Bad Request) if the pipeline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pipelines")
    @Timed
    public ResponseEntity<PipelineDTO> createPipeline(@RequestBody PipelineDTO pipelineDTO) throws URISyntaxException {
        log.debug("REST request to save Pipeline : {}", pipelineDTO);

        if (pipelineDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pipeline cannot already have an ID")).body(null);
        }
        PipelineDTO result = pipelineService.save(pipelineDTO);
        return ResponseEntity.created(new URI("/api/pipelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pipelines : Updates an existing pipeline.
     *
     * @param pipelineDTO the pipelineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pipelineDTO,
     * or with status 400 (Bad Request) if the pipelineDTO is not valid,
     * or with status 500 (Internal Server Error) if the pipelineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pipelines")
    @Timed
    public ResponseEntity<PipelineDTO> updatePipeline(@RequestBody PipelineDTO pipelineDTO) throws URISyntaxException {
        log.debug("REST request to update Pipeline : {}", pipelineDTO);

        PipelineDTO result = pipelineService.update(pipelineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pipelineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pipelines : get all the pipelines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pipelines in body
     */
    @GetMapping("/pipelines")
    @Timed
    public ResponseEntity<List<PipelineDTO>> getAllPipelines(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pipelines");
        Page<PipelineDTO> page = pipelineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pipelines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pipelines/:id : get the "id" pipeline.
     *
     * @param id the id of the pipelineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pipelineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pipelines/{id}")
    @Timed
    public ResponseEntity<PipelineDTO> getPipeline(@PathVariable Long id) {
        log.debug("REST request to get Pipeline : {}", id);
        PipelineDTO pipelineDTO = pipelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pipelineDTO));
    }

    /**
     * DELETE  /pipelines/:id : delete the "id" pipeline.
     *
     * @param id the id of the pipelineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pipelines/{id}")
    @Timed
    public ResponseEntity<PipelineDTO> deletePipeline(@PathVariable Long id) {
        log.debug("REST request to delete Pipeline : {}", id);

        pipelineService.delete(id);

        PipelineDTO result = new PipelineDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);

    }
}
