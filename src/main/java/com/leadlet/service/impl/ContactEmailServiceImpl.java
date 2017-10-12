package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
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

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing ContactEmail.
 */
@Service
@Transactional
public class ContactEmailServiceImpl implements ContactEmailService {

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
        contactEmail.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
        contactEmail = contactEmailRepository.save(contactEmail);
        return contactEmailMapper.toDto(contactEmail);
    }

    /**
     * Update a contactEmail.
     *
     * @param contactEmailDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ContactEmailDTO update(ContactEmailDTO contactEmailDTO) {
        log.debug("Request to update ContactEmail : {}", contactEmailDTO);

        ContactEmail contactEmail = contactEmailMapper.toEntity(contactEmailDTO);
        ContactEmail contactEmailFromDb = contactEmailRepository.findOneByIdAndAppAccount(contactEmail.getId(), SecurityUtils.getCurrentUserAppAccount());

        if (contactEmailFromDb != null) {
            contactEmail.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
            contactEmail = contactEmailRepository.save(contactEmail);
            return contactEmailMapper.toDto(contactEmail);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the contactEmails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactEmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactEmails");
        return contactEmailRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(contactEmailMapper::toDto);
    }

    /**
     * Get one contactEmail by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactEmailDTO findOne(Long id) {
        log.debug("Request to get ContactEmail : {}", id);
        ContactEmail contactEmail = contactEmailRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return contactEmailMapper.toDto(contactEmail);
    }

    /**
     * Delete the  contactEmail by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactEmail : {}", id);
        ContactEmail contactEmailFromDb = contactEmailRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        if (contactEmailFromDb != null) {
            contactEmailRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
