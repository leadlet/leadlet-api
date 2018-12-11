package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.ActivityService;
import com.leadlet.service.dto.ActivityDTO;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Activity.
 */
@RestController
@RequestMapping("/api")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResource.class);

    private static final String ENTITY_NAME = "activity";

    private final ActivityService activityService;

    public ActivityResource(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * POST  /activities : Create a new activity.
     *
     * @param activityDTO the activityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityDTO, or with status 400 (Bad Request) if the activity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activities")
    @Timed
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Activity : {}", activityDTO);
        if (activityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new activity cannot already have an ID")).body(null);
        }
        ActivityDTO result = activityService.save(activityDTO);
        return ResponseEntity.created(new URI("/api/activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activities : Updates an existing activity.
     *
     * @param activityDTO the activityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityDTO,
     * or with status 400 (Bad Request) if the activityDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activities")
    @Timed
    public ResponseEntity<ActivityDTO> updateActivity(@RequestBody ActivityDTO activityDTO) throws IOException {
        log.debug("REST request to update Activity : {}", activityDTO);

        ActivityDTO result = activityService.update(activityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityDTO.getId().toString()))
            .body(result);
    }


    @GetMapping("/activities")
    @Timed
    public ResponseEntity<List<ActivityDTO>> search(@ApiParam String q, @ApiParam Pageable pageable) throws URISyntaxException, IOException {

        Page<ActivityDTO> page = activityService.search(q, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activities/search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    /**
     * GET  /activities/:id : get the "id" activity.
     *
     * @param id the id of the activityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activities/{id}")
    @Timed
    public ResponseEntity<ActivityDTO> getActivity(@PathVariable Long id) {
        log.debug("REST request to get Activity : {}", id);
        ActivityDTO activityDTO = activityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activityDTO));
    }

    /**
     * DELETE  /activities/:id : delete the "id" activity.
     *
     * @param id the id of the activityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activities/{id}")
    @Timed
    public ResponseEntity<ActivityDTO> deleteActivity(@PathVariable Long id) {
        log.debug("REST request to delete Activity : {}", id);
        activityService.delete(id);

        ActivityDTO result = new ActivityDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);

    }

}
