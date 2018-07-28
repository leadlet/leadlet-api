package com.leadlet.service.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class StoragePreferenceDTO implements Serializable{

    private Long id;

    private String type;

    private String s3ApiKey;

    private String gsKeyFileName;

    private String gsBucketName;

    private boolean enabled;

    public String getType() {
        return type;
    }

    public StoragePreferenceDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getS3ApiKey() {
        return s3ApiKey;
    }

    public StoragePreferenceDTO setS3ApiKey(String s3ApiKey) {
        this.s3ApiKey = s3ApiKey;
        return this;
    }

    public String getGsKeyFileName() {
        return gsKeyFileName;
    }

    public StoragePreferenceDTO setGsKeyFileName(String gsKeyFileName) {
        this.gsKeyFileName = gsKeyFileName;
        return this;
    }

    public String getGsBucketName() {
        return gsBucketName;
    }

    public StoragePreferenceDTO setGsBucketName(String gsBucketName) {
        this.gsBucketName = gsBucketName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public StoragePreferenceDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public StoragePreferenceDTO setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}