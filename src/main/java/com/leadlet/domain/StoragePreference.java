package com.leadlet.domain;

import com.leadlet.domain.enumeration.StorageType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

@Entity
@Table(name = "storage_preference")
public class StoragePreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type" , nullable = false)
    @NotNull
    private StorageType type;

    @Column(name = "s3_api_key")
    private String s3ApiKey;

    @Lob
    @Column(name = "gs_key_file")
    private Blob gsKeyFile;

    @Column(name = "gs_key_file_name")
    private String gsKeyFileName;

    @Column(name = "gs_bucket_name")
    private String gsBucketName;

    public Long getId() {
        return id;
    }

    public StoragePreference setId(Long id) {
        this.id = id;
        return this;
    }

    public StorageType getType() {
        return type;
    }

    public StoragePreference setType(StorageType type) {
        this.type = type;
        return this;
    }

    public String getS3ApiKey() {
        return s3ApiKey;
    }

    public StoragePreference setS3ApiKey(String s3ApiKey) {
        this.s3ApiKey = s3ApiKey;
        return this;
    }

    public Blob getGsKeyFile() {
        return gsKeyFile;
    }

    public StoragePreference setGsKeyFile(Blob gsKeyFile) {
        this.gsKeyFile = gsKeyFile;
        return this;
    }

    public String getGsKeyFileName() {
        return gsKeyFileName;
    }

    public StoragePreference setGsKeyFileName(String gsKeyFileName) {
        this.gsKeyFileName = gsKeyFileName;
        return this;
    }

    public String getGsBucketName() {
        return gsBucketName;
    }

    public StoragePreference setGsBucketName(String gsBucketName) {
        this.gsBucketName = gsBucketName;
        return this;
    }
}
