package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.ObjectiveService;
import com.leadlet.service.dto.TeamObjectiveDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
import com.leadlet.service.dto.ObjectiveDTO;
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
 * REST controller for managing Objective.
 */
@RestController
@RequestMapping("/api")
public class ObjectiveResource {

    private final Logger log = LoggerFactory.getLogger(ObjectiveResource.class);

    private static final String ENTITY_NAME = "objective";

    private final ObjectiveService objectiveService;

    public ObjectiveResource(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    /**
     * POST  /objectives : Create a new objective.
     *
     * @param objectiveDTO the objectiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new objectiveDTO, or with status 400 (Bad Request) if the objective has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/objectives")
    @Timed
    public ResponseEntity<ObjectiveDTO> createObjective(@RequestBody ObjectiveDTO objectiveDTO) throws URISyntaxException {
        log.debug("REST request to save Objective : {}", objectiveDTO);
        if (objectiveDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new objective cannot already have an ID")).body(null);
        }
        ObjectiveDTO result = objectiveService.save(objectiveDTO);
        return ResponseEntity.created(new URI("/api/objectives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /objectives : Create a new objective.
     *
     * @param teamObjectiveDTO the objectiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new objectiveDTO, or with status 400 (Bad Request) if the objective has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/objectives/team")
    @Timed
    public ResponseEntity<TeamObjectiveDTO> createTeamObjective(@RequestBody TeamObjectiveDTO teamObjectiveDTO) throws URISyntaxException {
        log.debug("REST request to save Objective : {}", teamObjectiveDTO);

        TeamObjectiveDTO result = objectiveService.saveTeamObjective(teamObjectiveDTO);
        return ResponseEntity.created(new URI("/api/objectives/team" + result.getTeamId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getTeamId().toString()))
            .body(result);
    }

    /**
     * PUT  /objectives : Updates an existing objective.
     *
     * @param objectiveDTO the objectiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated objectiveDTO,
     * or with status 400 (Bad Request) if the objectiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the objectiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/objectives")
    @Timed
    public ResponseEntity<ObjectiveDTO> updateObjective(@RequestBody ObjectiveDTO objectiveDTO) throws URISyntaxException {
        log.debug("REST request to update Objective : {}", objectiveDTO);
        if (objectiveDTO.getId() == null) {
            return createObjective(objectiveDTO);
        }
        ObjectiveDTO result = objectiveService.save(objectiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, objectiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /objectives : get all the objectives.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of objectives in body
     */
    @GetMapping("/objectives")
    @Timed
    public ResponseEntity<List<ObjectiveDTO>> getAllObjectives(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Objectives");
        Page<ObjectiveDTO> page = objectiveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/objectives");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /objectives/:id : get the "id" objective.
     *
     * @param id the id of the objectiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objectiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/objectives/{id}")
    @Timed
    public ResponseEntity<ObjectiveDTO> getObjective(@PathVariable Long id) {
        log.debug("REST request to get Objective : {}", id);
        ObjectiveDTO objectiveDTO = objectiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(objectiveDTO));
    }

    /**
     * GET  /objectives/team/:id : get the "id" objective.
     *
     * @param teamId the id of the objectiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objectiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/objectives/team/{teamId}")
    @Timed
    public ResponseEntity<List<TeamObjectiveDTO>> getTeamObjectives(@PathVariable Long teamId) {
        log.debug("REST request to get Objective : {}", teamId);

        List<TeamObjectiveDTO> teamObjectiveDTOS = objectiveService.findAllByTeamId(teamId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teamObjectiveDTOS));
    }

    /**
     * DELETE  /objectives/:id : delete the "id" objective.
     *
     * @param id the id of the objectiveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/objectives/{id}")
    @Timed
    public ResponseEntity<Void> deleteObjective(@PathVariable Long id) {
        log.debug("REST request to delete Objective : {}", id);
        objectiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
