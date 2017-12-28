package com.leadlet.service.impl;

import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactPhoneService;
import com.leadlet.domain.ContactPhone;
import com.leadlet.repository.ContactPhoneRepository;
import com.leadlet.service.dto.ContactPhoneDTO;
import com.leadlet.service.mapper.ContactPhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing ContactPhone.
 */
@Service
@Transactional
public class ContactPhoneServiceImpl implements ContactPhoneService {

    private final Logger log = LoggerFactory.getLogger(ContactPhoneServiceImpl.class);

    private final ContactPhoneRepository contactPhoneRepository;

    private final ContactPhoneMapper contactPhoneMapper;

    public ContactPhoneServiceImpl(ContactPhoneRepository contactPhoneRepository, ContactPhoneMapper contactPhoneMapper) {
        this.contactPhoneRepository = contactPhoneRepository;
        this.contactPhoneMapper = contactPhoneMapper;
    }

    /**
     * Save a contactPhone.
     *
     * @param contactPhoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactPhoneDTO save(ContactPhoneDTO contactPhoneDTO) {
        log.debug("Request to save ContactPhone : {}", contactPhoneDTO);
        ContactPhone contactPhone = contactPhoneMapper.toEntity(contactPhoneDTO);
        contactPhone = contactPhoneRepository.save(contactPhone);
        return contactPhoneMapper.toDto(contactPhone);
    }

    /**
     * Update a contactPhone.
     *
     * @param contactPhoneDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ContactPhoneDTO update(ContactPhoneDTO contactPhoneDTO) {
        log.debug("Request to save ContactPhone : {}", contactPhoneDTO);
        ContactPhone contactPhone = contactPhoneMapper.toEntity(contactPhoneDTO);
        ContactPhone contactPhoneFromDb = contactPhoneRepository.findOneById(contactPhone.getId());

        if (contactPhoneFromDb != null) {
            contactPhone = contactPhoneRepository.save(contactPhone);
            return contactPhoneMapper.toDto(contactPhone);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the contactPhones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactPhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactPhones");
        return contactPhoneRepository.findAll(pageable)
            .map(contactPhoneMapper::toDto);
    }

    /**
     * Get one contactPhone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactPhoneDTO findOne(Long id) {
        log.debug("Request to get ContactPhone : {}", id);
        ContactPhone contactPhone = contactPhoneRepository.findOneById(id);
        return contactPhoneMapper.toDto(contactPhone);
    }

    /**
     * Delete the  contactPhone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactPhone : {}", id);
        ContactPhone contactPhone = contactPhoneRepository.findOneById(id);
        if (contactPhone != null){
            contactPhoneRepository.delete(id);
        }
        else {
            throw new EntityNotFoundException();
        }
    }
}
