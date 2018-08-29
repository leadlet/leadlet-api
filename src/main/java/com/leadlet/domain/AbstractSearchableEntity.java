package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadlet.domain.enumeration.ActivityType;
import com.leadlet.domain.enumeration.SyncStatus;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
public abstract class AbstractSearchableEntity extends AbstractAuditingEntity {

    @CreatedBy
    @Column(name = "sync_start_date", nullable = true, length = 50)
    @JsonIgnore
    private String syncStartDate;

    @CreatedBy
    @Column(name = "sync_end_date", nullable = true, length = 50)
    @JsonIgnore
    private String syncEndDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status" , nullable = false)
    @NotNull
    private SyncStatus syncStatus;

    public String getSyncStartDate() {
        return syncStartDate;
    }

    public AbstractSearchableEntity setSyncStartDate(String syncStartDate) {
        this.syncStartDate = syncStartDate;
        return this;
    }

    public String getSyncEndDate() {
        return syncEndDate;
    }

    public AbstractSearchableEntity setSyncEndDate(String syncEndDate) {
        this.syncEndDate = syncEndDate;
        return this;
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public AbstractSearchableEntity setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        return this;
    }
}
