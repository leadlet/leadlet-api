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

    private Long dealId;

    private Long agentId;

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

    public TimelineDTO setContactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }

    public Long getAgentId() {
        return agentId;
    }

    public TimelineDTO setAgentId(Long agentId) {
        this.agentId = agentId;
        return this;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TimelineDTO{" +
            "id=" + id +
            ", type=" + type +
            ", sourceId=" + sourceId +
            ", source=" + source +
            ", contactId=" + contactId +
            ", dealId=" + dealId +
            ", agentId=" + agentId +
            ", createdDate=" + createdDate +
            '}';
    }
}
