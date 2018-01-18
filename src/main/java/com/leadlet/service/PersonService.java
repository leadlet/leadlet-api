package com.leadlet.service;

import com.leadlet.service.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Person.
 */
public interface PersonService {

    /**
     * Save a person.
     *
     * @param personDTO the entity to save
     * @return the persisted entity
     */
    PersonDTO save(PersonDTO personDTO);

    /**
     * Update a person.
     *
     * @param personDTO the entity to update
     * @return the persisted entity
     */
    PersonDTO update(PersonDTO personDTO);

    /**
     *  Get all the persons.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" person.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonDTO findOne(Long id);

    /**
     *  Delete the "id" person.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Delete the "id" person.
     *
     *  @param idList the id of the entity
     */
    void delete(List<Long> idList);

    Page<PersonDTO> search(String filter, Pageable pageable);
}
