package com.leadlet.service.dto;

import java.io.Serializable;

public class StoragePreferenceDTO implements Serializable{

    private String apiKey;

    private String storageType;

    public StoragePreferenceDTO() {
    }

    public StoragePreferenceDTO(String storageType, String apiKey) {
        this.apiKey = apiKey;
        this.storageType = storageType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public StoragePreferenceDTO setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getStorageType() {
        return storageType;
    }

    public StoragePreferenceDTO setStorageType(String storageType) {
        this.storageType = storageType;
        return this;
    }
}
