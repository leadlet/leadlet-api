package com.leadlet.service;

import com.leadlet.domain.DocumentStorageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface DocumentStorageService {

    /**
     * Upload a document.
     *
     * @param multipartFile the entity to save
     * @return the uploaded file URL
     */
    DocumentStorageInfo upload(MultipartFile multipartFile) throws IOException;

    /**
     * Upload a document.
     *
     * @param documentStorageInfo the document to delete
     */
    boolean delete(DocumentStorageInfo documentStorageInfo) throws IOException;

}
