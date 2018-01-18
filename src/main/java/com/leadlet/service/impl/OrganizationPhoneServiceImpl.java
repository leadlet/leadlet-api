package com.leadlet.service.impl;

import com.leadlet.domain.OrganizationPhone;
import com.leadlet.repository.OrganizationPhoneRepository;
import com.leadlet.service.OrganizationPhoneService;
import com.leadlet.service.dto.OrganizationPhoneDTO;
import com.leadlet.service.mapper.OrganizationPhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing OrganizationPhone.
 */
@Service
@Transactional
public class OrganizationPhoneServiceImpl implements OrganizationPhoneService {

    private final Logger log = LoggerFactory.getLogger(OrganizationPhoneServiceImpl.class);

    private final OrganizationPhoneRepository organizationPhoneRepository;

    private final OrganizationPhoneMapper organizationPhoneMapper;

    public OrganizationPhoneServiceImpl(OrganizationPhoneRepository organizationPhoneRepository, OrganizationPhoneMapper organizationPhoneMapper) {
        this.organizationPhoneRepository = organizationPhoneRepository;
        this.organizationPhoneMapper = organizationPhoneMapper;
    }

    /**
     * Save a organizationPhone.
     *
     * @param organizationPhoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizationPhoneDTO save(OrganizationPhoneDTO organizationPhoneDTO) {
        log.debug("Request to save OrganizationPhone : {}", organizationPhoneDTO);
        OrganizationPhone organizationPhone = organizationPhoneMapper.toEntity(organizationPhoneDTO);
        organizationPhone = organizationPhoneRepository.save(organizationPhone);
        return organizationPhoneMapper.toDto(organizationPhone);
    }

    /**
     * Update a organizationPhone.
     *
     * @param organizationPhoneDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public OrganizationPhoneDTO update(OrganizationPhoneDTO organizationPhoneDTO) {
        log.debug("Request to save OrganizationPhone : {}", organizationPhoneDTO);
        OrganizationPhone organizationPhone = organizationPhoneMapper.toEntity(organizationPhoneDTO);
        OrganizationPhone organizationPhoneFromDb = organizationPhoneRepository.findOneById(organizationPhone.getId());

        if (organizationPhoneFromDb != null) {
            organizationPhone = organizationPhoneRepository.save(organizationPhone);
            return organizationPhoneMapper.toDto(organizationPhone);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the organizationPhones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationPhoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationPhone");
        return organizationPhoneRepository.findAll(pageable)
            .map(organizationPhoneMapper::toDto);
    }

    /**
     * Get one organizationPhone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationPhoneDTO findOne(Long id) {
        log.debug("Request to get OrganizationPhone : {}", id);
        OrganizationPhone organizationPhone = organizationPhoneRepository.findOneById(id);
        return organizationPhoneMapper.toDto(organizationPhone);
    }

    /**
     * Delete the  organizationPhone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationPhone : {}", id);
        OrganizationPhone organizationPhone = organizationPhoneRepository.findOneById(id);
        if (organizationPhone != null){
            organizationPhoneRepository.delete(id);
        }
        else {
            throw new EntityNotFoundException();
        }
    }
}
