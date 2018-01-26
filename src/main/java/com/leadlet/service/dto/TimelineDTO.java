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

    private Long dealId;

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

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineDTO)) return false;
        TimelineDTO that = (TimelineDTO) o;
        return Objects.equals(id, that.id) &&
            type == that.type &&
            Objects.equals(sourceId, that.sourceId) &&
            Objects.equals(source, that.source) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(dealId, that.dealId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, sourceId, source, personId, organizationId, dealId, userId, createdDate);
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
            ", dealId=" + dealId +
            ", userId=" + userId +
            ", createdDate=" + createdDate +
            '}';
    }
}
