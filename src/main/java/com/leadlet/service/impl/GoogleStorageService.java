package com.leadlet.service.impl;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.leadlet.domain.StoragePreference;
import com.leadlet.service.DocumentStorageService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("session")
public class GoogleStorageService implements DocumentStorageService{

    private StoragePreference storagePreference;

    private Bucket bucket;
    private Storage storage;

    public GoogleStorageService(StoragePreference storagePreference) {
        this.storagePreference = storagePreference;
    }

    @PostConstruct
    public void init() throws IOException, SQLException {
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        this.storage = StorageOptions.newBuilder()
            .setCredentials(ServiceAccountCredentials.fromStream(storagePreference.getGsKeyFile().getBinaryStream()))
            .build()
            .getService();

        this.bucket = storage.get(storagePreference.getGsBucketName());

    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        Blob blob = bucket.create(multipartFile.getName(), multipartFile.getInputStream());

        return blob.getMediaLink();
    }

    @Override
    public boolean delete(String documentName) throws IOException {

        BlobId blobId = BlobId.of(storagePreference.getGsBucketName(), documentName);
        boolean deleted = storage.delete(blobId);

        return deleted;
    }
}
