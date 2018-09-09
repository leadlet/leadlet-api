package com.leadlet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.leadlet.service.PersonService;
import com.leadlet.service.dto.PersonDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    /**
     * POST  /persons : Create a new person.
     *
     * @param personDTO the personDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personDTO, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/persons")
    @Timed
    public ResponseEntity<PersonDTO> createPerson(@Validated @RequestBody PersonDTO personDTO) throws URISyntaxException {
        log.debug("REST request to save Person : {}", personDTO);
        if (personDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
        }
        PersonDTO result = personService.save(personDTO);
        return ResponseEntity.created(new URI("/api/persons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /persons : Updates an existing person.
     *
     * @param personDTO the personDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personDTO,
     * or with status 400 (Bad Request) if the personDTO is not valid,
     * or with status 500 (Internal Server Error) if the personDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/persons")
    @Timed
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO) throws URISyntaxException {
        log.debug("REST request to update Person : {}", personDTO);

        PersonDTO result = personService.update(personDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /persons : get all the persons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of persons in body
     */
    @GetMapping("/persons")
    @Timed
    public ResponseEntity<List<PersonDTO>> getAllPersons(@ApiParam String filter, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Persons");

        Page<PersonDTO> page = personService.search(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /persons/:id : get the "id" person.
     *
     * @param id the id of the personDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personDTO, or with status 404 (Not Found)
     */
    @GetMapping("/persons/{id}")
    @Timed
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        PersonDTO personDTO = personService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personDTO));
    }

    /**
     * DELETE  /persons/:idList : delete the "idList" person.
     *
     * @param idList the id of the personDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/persons/{idList}")
    @Timed
    public ResponseEntity<List<Long>> deletePerson(@PathVariable List<Long> idList) {
        log.debug("REST request to delete Person : {}", idList);
        personService.delete(idList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME,""))
            .body(idList);
    }
}
