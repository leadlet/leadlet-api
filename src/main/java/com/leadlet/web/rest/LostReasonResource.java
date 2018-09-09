package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.LostReasonService;
import com.leadlet.service.dto.LostReasonDTO;
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
 * REST controller for managing Lost Reason.
 */
@RestController
@RequestMapping("/api")
public class LostReasonResource {

    private final Logger log = LoggerFactory.getLogger(LostReasonResource.class);

    private static final String ENTITY_NAME = "lostReason";

    private final LostReasonService lostReasonService;

    public LostReasonResource(LostReasonService lostReasonService) {
        this.lostReasonService = lostReasonService;
    }

    /**
     * POST  /lost-reasons : Create a new lost reason.
     *
     * @param lostReasonDTO the lostReasonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lostReasonDTO, or with status 400 (Bad Request) if the lost reason has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lost-reasons")
    @Timed
    public ResponseEntity<LostReasonDTO> createLostReason(@RequestBody LostReasonDTO lostReasonDTO) throws URISyntaxException {
        log.debug("REST request to save Lost Reason : {}", lostReasonDTO);
        if (lostReasonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lost reason cannot already have an ID")).body(null);
        }
        LostReasonDTO result = lostReasonService.save(lostReasonDTO);
        return ResponseEntity.created(new URI("/api/lost-reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lost-reasons : Updates an existing lost reason.
     *
     * @param lostReasonDTO the lostReasonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lostReasonDTO,
     * or with status 400 (Bad Request) if the lostReasonDTO is not valid,
     * or with status 500 (Internal Server Error) if the lostReasonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lost-reasons")
    @Timed
    public ResponseEntity<LostReasonDTO> updateLostReason(@RequestBody LostReasonDTO lostReasonDTO) throws URISyntaxException {
        log.debug("REST request to update Lost Reason : {}", lostReasonDTO);

        LostReasonDTO result = lostReasonService.update(lostReasonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lostReasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lost-reasons : get all the lost reasons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lost reasons in body
     */
    @GetMapping("/lost-reasons")
    @Timed
    public ResponseEntity<List<LostReasonDTO>> getAllLostReasons(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of lost reasons");
        Page<LostReasonDTO> page = lostReasonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lost-reasons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lost-reasons/:id : get the "id" lost reasons.
     *
     * @param id the id of the lostReasonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lostReasonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lost-reasons/{id}")
    @Timed
    public ResponseEntity<LostReasonDTO> getLostReason(@PathVariable Long id) {
        log.debug("REST request to get Lost Reason : {}", id);
        LostReasonDTO lostReasonDTO = lostReasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lostReasonDTO));
    }

    /**
     * DELETE  /lost-reasons/:id : delete the "id" lost reason.
     *
     * @param id the id of the lostReasonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lost-reasons/{id}")
    @Timed
    public ResponseEntity<LostReasonDTO> deleteLostReason(@PathVariable Long id) {
        log.debug("REST request to delete Lost Reason : {}", id);
        lostReasonService.delete(id);

        LostReasonDTO result = new LostReasonDTO();
        result.setId(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }
}
