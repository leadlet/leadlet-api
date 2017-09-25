package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @NotNull
    private AppAccount appAccount;

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    public AbstractAccountSpecificEntity appAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
        return this;
    }

}
