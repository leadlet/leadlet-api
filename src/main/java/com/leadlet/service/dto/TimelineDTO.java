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

    private Long contactId;

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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
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
        return Objects.equals(id, that.id) &&
            type == that.type &&
            Objects.equals(sourceId, that.sourceId) &&
            Objects.equals(source, that.source) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, sourceId, source, contactId, userId, createdDate);
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
            "id=" + id +
            ", type=" + type +
            ", sourceId=" + sourceId +
            ", source=" + source +
            ", contactId=" + contactId +
            ", userId=" + userId +
            ", createdDate=" + createdDate +
            '}';
    }
}
