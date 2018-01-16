package com.leadlet.service.impl;

import com.leadlet.domain.ContactPhone;
import com.leadlet.domain.Organization;
import com.leadlet.repository.OrganizationRepository;
import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.repository.util.SpecificationsBuilder;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactPhoneService;
import com.leadlet.service.OrganizationService;
import com.leadlet.service.dto.ContactPhoneDTO;
import com.leadlet.service.dto.OrganizationDTO;
import com.leadlet.service.mapper.OrganizationMapper;
import com.leadlet.web.rest.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final ContactPhoneService contactPhoneService;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrganizationMapper organizationMapper,
                                   ContactPhoneService contactPhoneService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.contactPhoneService = contactPhoneService;
    }

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());

        organization = organizationRepository.save(organization);

        /*
        Set<ContactPhone> phones = new HashSet<>();
        for ( ContactPhoneDTO phoneDTO: organizationDTO.getPhones()) {
            ContactPhoneDTO phone = contactPhoneService.save(phoneDTO);
            phones.add(phone);
        }

        organization.setPhones(phones);
        organization = organizationRepository.save(organization);
        */

        return organizationMapper.toDto(organization);
    }

    /**
     * Update a organization.
     *
     * @param organizationDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public OrganizationDTO update(OrganizationDTO organizationDTO) {
        log.debug("Request to update Organization : {}", organizationDTO);

        Organization organization = organizationMapper.toEntity(organizationDTO);
        Organization organizationFromDb = organizationRepository.findOneByIdAndAppAccount_Id(organization.getId(), SecurityUtils.getCurrentUserAppAccountId());
        if (organizationFromDb != null) {
            organization.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());
            organization = organizationRepository.save(organization);
            return organizationMapper.toDto(organization);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId(), pageable)
            .map(organizationMapper::toDto);
    }

    @Override
    public Page<OrganizationDTO> search(String filter, Pageable pageable) {
        log.debug("Request to get all Organizations");
        SpecificationsBuilder builder = new SpecificationsBuilder();

        if (!StringUtils.isEmpty(filter)) {
            List<SearchCriteria> criteriaList = ParameterUtil.createCriterias(filter);

            for (SearchCriteria criteria : criteriaList) {
                builder.with(criteria);
            }

        }

        // TODO add account criteria
        // builder.with("appAccount",":", SecurityUtils.getCurrentUserAppAccountReference());

        Specification<Organization> spec = builder.build();

        return organizationRepository.findAll(spec, pageable)
            .map(organizationMapper::toDto);

    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationDTO findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        Organization organization = organizationRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        return organizationMapper.toDto(organization);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);

        Organization organization = organizationRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (organization != null) {
            organizationRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
