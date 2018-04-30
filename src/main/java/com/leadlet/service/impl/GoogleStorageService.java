package com.leadlet.service.impl;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.leadlet.domain.DocumentStorageInfo;
import com.leadlet.domain.StoragePreference;
import com.leadlet.service.DocumentStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoogleStorageService implements DocumentStorageService{

    private StoragePreference storagePreference;

    private Bucket bucket;
    private Storage storage;

    public GoogleStorageService(StoragePreference storagePreference) {
        this.storagePreference = storagePreference;
    }

    public GoogleStorageService init() throws IOException, SQLException {
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        this.storage = StorageOptions.newBuilder()
            .setCredentials(ServiceAccountCredentials.fromStream(storagePreference.getGsKeyFile().getBinaryStream()))
            .build()
            .getService();

        this.bucket = storage.get(storagePreference.getGsBucketName());

        return this;

    }

    @Override
    public DocumentStorageInfo upload(MultipartFile multipartFile) throws IOException {
        Blob blob = bucket.create(multipartFile.getOriginalFilename(), multipartFile.getInputStream());

        return new DocumentStorageInfo(blob);
    }

    @Override
    public boolean delete(DocumentStorageInfo documentStorageInfo) throws IOException {

        BlobId blobId = BlobId.of(documentStorageInfo.getGsBucket(), documentStorageInfo.getGsName(),documentStorageInfo.getGsGeneration());
        boolean deleted = storage.delete(blobId);

        return deleted;
    }

}
