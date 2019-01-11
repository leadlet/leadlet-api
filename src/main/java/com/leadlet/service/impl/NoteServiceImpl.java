package com.leadlet.service.impl;

import com.leadlet.domain.Note;
import com.leadlet.repository.NoteRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.NoteService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.NoteDTO;
import com.leadlet.service.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

/**
 * Service Implementation for managing Note.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {


    private final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final TimelineService timelineService;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper, TimelineService timelineService) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.timelineService = timelineService;
    }

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NoteDTO save(NoteDTO noteDTO) throws IOException {
        log.debug("Request to save Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
        note = noteRepository.save(note);
        timelineService.noteCreated(note);
        return noteMapper.toDto(note);
    }

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        return noteRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(noteMapper::toDto);
    }

    /**
     * Get one note by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NoteDTO findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        Note note = noteRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return noteMapper.toDto(note);
    }

    /**
     * Delete the note by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        Note noteFromDb = noteRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (noteFromDb != null) {
            noteRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
