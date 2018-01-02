package com.leadlet.service;

import com.leadlet.service.dto.NoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save
     * @return the persisted entity
     */
    NoteDTO save(NoteDTO noteDTO);

    /**
     * Update a note.
     *
     * @param noteDTO the entity to update
     * @return the persisted entity
     */
    NoteDTO update(NoteDTO noteDTO);

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NoteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" note.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NoteDTO findOne(Long id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
