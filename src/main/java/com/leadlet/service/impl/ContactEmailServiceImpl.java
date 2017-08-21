package com.leadlet.service.impl;

import com.leadlet.service.ContactEmailService;
import com.leadlet.domain.ContactEmail;
import com.leadlet.repository.ContactEmailRepository;
import com.leadlet.service.dto.ContactEmailDTO;
import com.leadlet.service.mapper.ContactEmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContactEmail.
 */
@Service
@Transactional
public class ContactEmailServiceImpl implements ContactEmailService{

    private final Logger log = LoggerFactory.getLogger(ContactEmailServiceImpl.class);

    private final ContactEmailRepository contactEmailRepository;

    private final ContactEmailMapper contactEmailMapper;

    public ContactEmailServiceImpl(ContactEmailRepository contactEmailRepository, ContactEmailMapper contactEmailMapper) {
        this.contactEmailRepository = contactEmailRepository;
        this.contactEmailMapper = contactEmailMapper;
    }

    /**
     * Save a contactEmail.
     *
     * @param contactEmailDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactEmailDTO save(ContactEmailDTO contactEmailDTO) {
        log.debug("Request to save ContactEmail : {}", contactEmailDTO);
        ContactEmail contactEmail = contactEmailMapper.toEntity(contactEmailDTO);
        contactEmail = contactEmailRepository.save(contactEmail);
        return contactEmailMapper.toDto(contactEmail);
    }

    /**
     *  Get all the contactEmails.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactEmails");
        return contactEmailRepository.findAll(pageable)
            .map(contactEmailMapper::toDto);
    }

    /**
     *  Get one contactEmail by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactEmailDTO findOne(Long id) {
        log.debug("Request to get ContactEmail : {}", id);
        ContactEmail contactEmail = contactEmailRepository.findOne(id);
        return contactEmailMapper.toDto(contactEmail);
    }

    /**
     *  Delete the  contactEmail by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactEmail : {}", id);
        contactEmailRepository.delete(id);
    }
}
