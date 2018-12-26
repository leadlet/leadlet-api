package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.TimelineItemType;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class TimelineDTO implements Serializable {

    private Long id;

    private TimelineItemType type;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
            ", content='" + content + '\'' +
            ", contactId=" + contactId +
            ", dealId=" + dealId +
            ", agentId=" + agentId +
            ", createdDate=" + createdDate +
            '}';
    }
}
