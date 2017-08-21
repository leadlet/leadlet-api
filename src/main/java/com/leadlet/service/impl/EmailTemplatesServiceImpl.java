package com.leadlet.service.impl;

import com.leadlet.service.EmailTemplatesService;
import com.leadlet.domain.EmailTemplates;
import com.leadlet.repository.EmailTemplatesRepository;
import com.leadlet.service.dto.EmailTemplatesDTO;
import com.leadlet.service.mapper.EmailTemplatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmailTemplates.
 */
@Service
@Transactional
public class EmailTemplatesServiceImpl implements EmailTemplatesService{

    private final Logger log = LoggerFactory.getLogger(EmailTemplatesServiceImpl.class);

    private final EmailTemplatesRepository emailTemplatesRepository;

    private final EmailTemplatesMapper emailTemplatesMapper;

    public EmailTemplatesServiceImpl(EmailTemplatesRepository emailTemplatesRepository, EmailTemplatesMapper emailTemplatesMapper) {
        this.emailTemplatesRepository = emailTemplatesRepository;
        this.emailTemplatesMapper = emailTemplatesMapper;
    }

    /**
     * Save a emailTemplates.
     *
     * @param emailTemplatesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailTemplatesDTO save(EmailTemplatesDTO emailTemplatesDTO) {
        log.debug("Request to save EmailTemplates : {}", emailTemplatesDTO);
        EmailTemplates emailTemplates = emailTemplatesMapper.toEntity(emailTemplatesDTO);
        emailTemplates = emailTemplatesRepository.save(emailTemplates);
        return emailTemplatesMapper.toDto(emailTemplates);
    }

    /**
     *  Get all the emailTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailTemplatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailTemplates");
        return emailTemplatesRepository.findAll(pageable)
            .map(emailTemplatesMapper::toDto);
    }

    /**
     *  Get one emailTemplates by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmailTemplatesDTO findOne(Long id) {
        log.debug("Request to get EmailTemplates : {}", id);
        EmailTemplates emailTemplates = emailTemplatesRepository.findOne(id);
        return emailTemplatesMapper.toDto(emailTemplates);
    }

    /**
     *  Delete the  emailTemplates by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailTemplates : {}", id);
        emailTemplatesRepository.delete(id);
    }
}
