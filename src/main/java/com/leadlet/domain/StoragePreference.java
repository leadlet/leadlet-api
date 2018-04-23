package com.leadlet.domain;

import com.leadlet.domain.enumeration.StorageType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class StoragePreference {

    @Column(name = "storage_api_key")
    private String apiKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type")
    private StorageType storageType;

    public StoragePreference() {
    }

    public StoragePreference(StorageType storageType, String apiKey ) {
        this.apiKey = apiKey;
        this.storageType = storageType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public StoragePreference setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public StoragePreference setStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }
}
