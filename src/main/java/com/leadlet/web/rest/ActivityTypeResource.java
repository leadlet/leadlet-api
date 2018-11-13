package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.ActivityTypeService;
import com.leadlet.service.dto.ActivityTypeDTO;
import com.leadlet.web.rest.util.HeaderUtil;
import com.leadlet.web.rest.util.PaginationUtil;
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
 * REST controller for managing Activity Type.
 */
@RestController
@RequestMapping("/api")
public class ActivityTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActivityTypeResource.class);

    private static final String ENTITY_NAME = "activity_type";

    private final ActivityTypeService activityTypeService;

    public ActivityTypeResource(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    /**
     * POST  /activity-types : Create a new activity type.
     *
     * @param activityTypeDTO the activityTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityTypeDTO, or with status 400 (Bad Request) if the activity type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activity-types")
    @Timed
    public ResponseEntity<ActivityTypeDTO> createActivityType(@RequestBody ActivityTypeDTO activityTypeDTO) throws URISyntaxException {
        log.debug("REST request to save Activity Type : {}", activityTypeDTO);
        if (activityTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new activity type cannot already have an ID")).body(null);
        }
        ActivityTypeDTO result = activityTypeService.save(activityTypeDTO);
        return ResponseEntity.created(new URI("/api/activity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activity-types : Updates an existing activity type.
     *
     * @param activityTypeDTO the activityTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityTypeDTO,
     * or with status 400 (Bad Request) if the activityTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activity-types")
    @Timed
    public ResponseEntity<ActivityTypeDTO> updateActivityTYpe(@RequestBody ActivityTypeDTO activityTypeDTO) throws URISyntaxException {
        log.debug("REST request to update  Activity Type : {}", activityTypeDTO);

        ActivityTypeDTO result = activityTypeService.update(activityTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activity-types : get all the activity types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activity types in body
     */
    @GetMapping("/activity-types")
    @Timed
    public ResponseEntity<List<ActivityTypeDTO>> getAllActivityTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Activity Type");
        Page<ActivityTypeDTO> page = activityTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activity-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activity-types/:id : get the "id" activity type.
     *
     * @param id the id of the activityTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activity-types/{id}")
    @Timed
    public ResponseEntity<ActivityTypeDTO> getActivityType(@PathVariable Long id) {
        log.debug("REST request to get Activity Type : {}", id);
        ActivityTypeDTO activityTypeDTO = activityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activityTypeDTO));
    }

    /**
     * DELETE  /activity-types/:id : delete the "id" activity type.
     *
     * @param id the id of the activityTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activity-types/{id}")
    @Timed
    public ResponseEntity<ActivityTypeDTO> deleteActivityType(@PathVariable Long id) {
        log.debug("REST request to delete Activity Type : {}", id);
        activityTypeService.delete(id);

        ActivityTypeDTO result = new ActivityTypeDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }
}
