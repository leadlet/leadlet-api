package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactService;
import com.leadlet.domain.Contact;
import com.leadlet.repository.ContactRepository;
import com.leadlet.service.dto.ContactDTO;
import com.leadlet.service.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    /**
     * Update a contact.
     *
     * @param contactDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ContactDTO update(ContactDTO contactDTO) {
        log.debug("Request to update Contact : {}", contactDTO);

        Contact contact = contactMapper.toEntity(contactDTO);
        Contact contactFromDb = contactRepository.findOneByIdAndAppAccount(contact.getId(), SecurityUtils.getCurrentUserAppAccount());
        if (contactFromDb != null) {
            contact.setAppAccount(SecurityUtils.getCurrentUserAppAccount());
            contact = contactRepository.save(contact);
            return contactMapper.toDto(contact);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findByAppAccount(SecurityUtils.getCurrentUserAppAccount(), pageable)
            .map(contactMapper::toDto);
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactDTO findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        Contact contact = contactRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        return contactMapper.toDto(contact);
    }

    /**
     * Delete the  contact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);

        Contact contact = contactRepository.findOneByIdAndAppAccount(id, SecurityUtils.getCurrentUserAppAccount());
        if (contact != null) {
            contactRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
