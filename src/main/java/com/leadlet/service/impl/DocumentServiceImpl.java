package com.leadlet.service.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.leadlet.domain.Document;
import com.leadlet.domain.DocumentStorageInfo;
import com.leadlet.domain.Organization;
import com.leadlet.domain.Person;
import com.leadlet.repository.DocumentRepository;
import com.leadlet.repository.OrganizationRepository;
import com.leadlet.repository.PersonRepository;
import com.leadlet.security.SecurityUtils;
import com.leadlet.service.AppAccountService;
import com.leadlet.service.DocumentService;
import com.leadlet.service.DocumentStorageService;
import com.leadlet.service.TimelineService;
import com.leadlet.service.dto.DocumentDTO;
import com.leadlet.service.mapper.DocumentMapper;
import com.leadlet.service.util.DocumentStorageServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Document.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final AppAccountService appAccountService;

    private final DocumentRepository documentRepository;

    private final PersonRepository personRepository;

    private final OrganizationRepository organizationRepository;

    private final DocumentMapper documentMapper;

    private final TimelineService timelineService;

    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper, PersonRepository personRepository, OrganizationRepository organizationRepository, TimelineService timelineService,AppAccountService appAccountService) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.personRepository = personRepository;
        this.organizationRepository = organizationRepository;
        this.timelineService = timelineService;
        this.appAccountService = appAccountService;
    }

    @Override
    public DocumentDTO save(MultipartFile multipartFile, long personId) throws IOException, DbxException, SQLException {

        DocumentStorageService storageService = DocumentStorageServiceFactory.getService(appAccountService.getCurrent().getStoragePreference());

        DocumentStorageInfo documentStorageInfo = storageService.upload(multipartFile);

        Document document = new Document();
        document.setDocumentStorageInfo(documentStorageInfo);
        document.setName(multipartFile.getOriginalFilename());
        document.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());

        Person person = personRepository.findOneByIdAndAppAccount_Id(personId, SecurityUtils.getCurrentUserAppAccountId());
        document.setPerson(person);

        document = documentRepository.save(document);

        timelineService.documentCreated(document);

        return documentMapper.toDto(document);

    }

    @Override
    public DocumentDTO saveDocumentForOrganization(MultipartFile multipartFile, long organizationId) throws IOException, DbxException, SQLException {

        DocumentStorageService storageService = DocumentStorageServiceFactory.getService(appAccountService.getCurrent().getStoragePreference());

        DocumentStorageInfo documentStorageInfo = storageService.upload(multipartFile);

        Document document = new Document();
        document.setDocumentStorageInfo(documentStorageInfo);
        document.setName(multipartFile.getName());
        document.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());

        Organization organization = organizationRepository.findOneByIdAndAppAccount_Id(organizationId, SecurityUtils.getCurrentUserAppAccountId());
        document.setOrganization(organization);

        document = documentRepository.save(document);

        timelineService.documentCreated(document);

        return documentMapper.toDto(document);
    }

    @Override
    public DocumentDTO update(DocumentDTO documentDTO) {

        log.debug("Request to update Document : {}", documentDTO);
        Document document = documentMapper.toEntity(documentDTO);
        Document oldDocument = documentRepository.findOneByIdAndAppAccount_Id(document.getId(), SecurityUtils.getCurrentUserAppAccountId());

        if (oldDocument != null) {
            // TODO appaccount'u eklemek dogru fakat appaccount olmadan da kayit hatasi almaliydik.
            document.setAppAccount(SecurityUtils.getCurrentUserAppAccountReference());

            document = documentRepository.save(document);
            return documentMapper.toDto(document);

        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<DocumentDTO> findAll() {
        log.debug("Request to get all Documents");

        List<Document> documentList = documentRepository.findAllByAppAccount_Id(SecurityUtils.getCurrentUserAppAccountId());
        return documentMapper.toDto(documentList);
    }

    @Override
    public List<DocumentDTO> findByPersonId(Long personId) {
        List<Document> documentList = documentRepository.findByPerson_IdAndAppAccount_Id(personId, SecurityUtils.getCurrentUserAppAccountId());

        return documentMapper.toDto(documentList);
    }

    @Override
    public List<DocumentDTO> findByOrganizationId(Long organizationId) {
        List<Document> documentList = documentRepository.findByOrganization_IdAndAppAccount_Id(organizationId, SecurityUtils.getCurrentUserAppAccountId());

        return documentMapper.toDto(documentList);
    }

    @Override
    public List<DocumentDTO> findByDealId(Long dealId) {
        List<Document> documentList = documentRepository.findByDeal_IdAndAppAccount_Id(dealId, SecurityUtils.getCurrentUserAppAccountId());

        return documentMapper.toDto(documentList);
    }

    @Override
    public void delete(Long id) throws IOException, SQLException {
        log.debug("Request to delete Document : {}", id);

        DocumentStorageService storageService = DocumentStorageServiceFactory.getService(appAccountService.getCurrent().getStoragePreference());

        Document documentFromDb = documentRepository.findOneByIdAndAppAccount_Id(id, SecurityUtils.getCurrentUserAppAccountId());
        if (documentFromDb != null) {
            boolean deleted = storageService.delete(documentFromDb.getDocumentStorageInfo());
            if (deleted) {
                documentRepository.delete(id);
            } else {
                throw new EntityNotFoundException("The blob can not delete");
            }
        } else {
            throw new EntityNotFoundException();
        }
    }
}
