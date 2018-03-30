package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.ActivityType;

import java.time.Instant;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the Activity entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    private String title;

    private String memo;

    private Date start;

    private Date end;

    private ActivityType type;

    private Long dealId;

    private Long personId;

    private Long organizationId;

    private Long userId;

    private LocationDTO location;

    private boolean isClosed;

    private Instant closedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long contactId) {
        this.personId = contactId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long contactId) {
        this.organizationId = contactId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long appUserId) {
        this.userId = appUserId;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Instant getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Instant closedDate) {
        this.closedDate = closedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityDTO)) return false;
        ActivityDTO that = (ActivityDTO) o;
        return isClosed == that.isClosed &&
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            type == that.type &&
            Objects.equals(dealId, that.dealId) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(location, that.location) &&
            Objects.equals(closedDate, that.closedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", memo='" + memo + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", type=" + type +
            ", dealId=" + dealId +
            ", personId=" + personId +
            ", organizationId=" + organizationId +
            ", userId=" + userId +
            ", location=" + location +
            ", isClosed=" + isClosed +
            ", closedDate=" + closedDate +
            '}';
    }
}
