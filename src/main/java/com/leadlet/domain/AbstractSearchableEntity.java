package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadlet.domain.enumeration.SyncStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
public abstract class AbstractSearchableEntity extends AbstractAuditingEntity {

    @Column(name = "sync_start_date", length = 50)
    @JsonIgnore
    private Instant syncStartDate;

    @Column(name = "sync_end_date",length = 50)
    @JsonIgnore
    private Instant syncEndDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    @JsonIgnore
    private SyncStatus syncStatus = SyncStatus.NOT_SYNCED;

    public Instant getSyncStartDate() {
        return syncStartDate;
    }

    public AbstractSearchableEntity setSyncStartDate(Instant syncStartDate) {
        this.syncStartDate = syncStartDate;
        return this;
    }

    public Instant getSyncEndDate() {
        return syncEndDate;
    }

    public AbstractSearchableEntity setSyncEndDate(Instant syncEndDate) {
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
