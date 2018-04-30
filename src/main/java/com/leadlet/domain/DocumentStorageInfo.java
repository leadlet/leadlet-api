package com.leadlet.domain;

import com.google.cloud.storage.Blob;
import com.leadlet.domain.enumeration.StorageType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Document.
 */
@Embeddable
public class DocumentStorageInfo {

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type")
    private StorageType storageType;

    @Column(name = "gs_generation")
    private long gsGeneration;

    @Column(name = "gs_bucket")
    private String gsBucket;

    @Column(name = "gs_name")
    private String gsName;

    @Column(name = "document_url",length = 2100)
    private String documentUrl;

    public DocumentStorageInfo() {
    }

    public DocumentStorageInfo(Blob blob){
        this.storageType = StorageType.GOOGLE_STORAGE;
        this.gsBucket = blob.getBucket();
        this.gsGeneration = blob.getGeneration();
        this.documentUrl = blob.getMediaLink();
        this.gsName = blob.getName();
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public DocumentStorageInfo setStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    public long getGsGeneration() {
        return gsGeneration;
    }

    public DocumentStorageInfo setGsGeneration(long gsGeneration) {
        this.gsGeneration = gsGeneration;
        return this;
    }

    public String getGsBucket() {
        return gsBucket;
    }

    public DocumentStorageInfo setGsBucket(String gsBucket) {
        this.gsBucket = gsBucket;
        return this;
    }

    public String getGsName() {
        return gsName;
    }

    public DocumentStorageInfo setGsName(String gsName) {
        this.gsName = gsName;
        return this;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public DocumentStorageInfo setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }
}
