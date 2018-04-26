package com.leadlet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentStorageService {

    /**
     * Upload a document.
     *
     * @param multipartFile the entity to save
     * @return the uploaded file URL
     */
    String upload(MultipartFile multipartFile) throws IOException;

    /**
     * Upload a document.
     *
     * @param documentName the document to delete
     */
    boolean delete(String documentName) throws IOException;

}
