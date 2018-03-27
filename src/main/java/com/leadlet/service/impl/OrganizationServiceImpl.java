package com.leadlet.service.impl;

import com.leadlet.domain.ContactPhone;
import com.leadlet.domain.Organization;
import com.leadlet.domain.OrganizationPhone;
import com.leadlet.domain.Person;
import com.leadlet.repository.OrganizationRepository;
import com.leadlet.repository.util.SearchCriteria;
import com.leadlet.repository.util.SpecificationsBuilder;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.ContactPhoneService;
import com.leadlet.service.OrganizationService;
import com.leadlet.service.PersonService;
import com.leadlet.service.dto.ContactPhoneDTO;
import com.leadlet.service.dto.OrganizationDTO;
import com.leadlet.service.dto.PersonDTO;
import com.leadlet.service.mapper.OrganizationMapper;
import com.leadlet.web.rest.util.ParameterUtil;
import io.swagger.models.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
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

    private final PersonService personService;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrganizationMapper organizationMapper,
                                   ContactPhoneService contactPhoneService,
                                   PersonService personService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.contactPhoneService = contactPhoneService;
        this.personService = personService;
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

        Organization organizationFromDb = organizationRepository.save(organization);

        Set<OrganizationPhone> phones = organization.getPhones();
        Iterator<OrganizationPhone> iter = phones.iterator();
        while (iter.hasNext()) {
            iter.next().setOrganization(organizationFromDb);
        }

        organizationFromDb.setPhones(phones);

        organizationFromDb = organizationRepository.save(organizationFromDb);
        return organizationMapper.toDto(organizationFromDb);
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

            Set<OrganizationPhone> phones = organization.getPhones();
            Iterator<OrganizationPhone> iter = phones.iterator();
            while (iter.hasNext()) {
                iter.next().setOrganization(organizationFromDb);
            }

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
        log.debug("Request to get all Persons");

        Specifications<Organization> searchSpecs = buildSpecificationsFromFilter(filter);

        return organizationRepository.findAll(searchSpecs, pageable)
            .map(organizationMapper::toDto);

    }

    private Specifications<Organization> buildSpecificationsFromFilter(String filter) {

        Specification<Organization> appAccount = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("appAccount"), SecurityUtils.getCurrentUserAppAccountId());

        Specifications<Organization> searchSpec = Specifications.where(appAccount);

        if (!StringUtils.isEmpty(filter)) {
            List<SearchCriteria> criteriaList = ParameterUtil.createCriterias(filter);
            for (SearchCriteria criteria : criteriaList) {
                if( criteria.getKey().equals("name")){
                    Specification<Organization> nameLike = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+criteria.getValue()+"%");
                    searchSpec = searchSpec.and(nameLike);
                }else if( criteria.getKey().equals("person")){
                    Specification<Organization> hasOrganization = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("organization"), Long.parseLong(criteria.getValue().toString()));
                    searchSpec = searchSpec.and(hasOrganization);
                }
            }
        }

        return searchSpec;

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

    @Override
    public void delete(List<Long> idList) {
        organizationRepository.deleteByIdInAndAppAccount_Id(idList, SecurityUtils.getCurrentUserAppAccountId());

    }
}
