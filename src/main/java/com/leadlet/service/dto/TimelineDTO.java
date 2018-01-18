package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.TimelineItemType;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class TimelineDTO implements Serializable {

    private Long id;

    private TimelineItemType type;

    private Long sourceId;

    private Object source;

    private Long personId;

    private Long organizationId;

    private Long userId;

    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimelineItemType getType() {
        return type;
    }

    public void setType(TimelineItemType type) {
        this.type = type;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getPersonId() {
        return personId;
    }

    public TimelineDTO setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public TimelineDTO setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineDTO)) return false;

        TimelineDTO that = (TimelineDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != that.type) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (organizationId != null ? !organizationId.equals(that.organizationId) : that.organizationId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return createdDate != null ? createdDate.equals(that.createdDate) : that.createdDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (organizationId != null ? organizationId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
            "id=" + id +
            ", type=" + type +
            ", sourceId=" + sourceId +
            ", source=" + source +
            ", personId=" + personId +
            ", organizationId=" + organizationId +
            ", userId=" + userId +
            ", createdDate=" + createdDate +
            '}';
    }
}
