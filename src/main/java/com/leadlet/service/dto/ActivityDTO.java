package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.ActivityType;

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

    private DealDTO deal;

    private PersonDTO person;

    private OrganizationDTO organization;

    private UserDTO agent;

    private LocationDTO location;

    private boolean done;

    private Date closedDate;

    public Long getId() {
        return id;
    }

    public ActivityDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ActivityDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public ActivityDTO setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public ActivityDTO setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public ActivityDTO setEnd(Date end) {
        this.end = end;
        return this;
    }

    public ActivityType getType() {
        return type;
    }

    public ActivityDTO setType(ActivityType type) {
        this.type = type;
        return this;
    }

    public DealDTO getDeal() {
        return deal;
    }

    public ActivityDTO setDeal(DealDTO deal) {
        this.deal = deal;
        return this;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public ActivityDTO setPerson(PersonDTO person) {
        this.person = person;
        return this;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public ActivityDTO setOrganization(OrganizationDTO organization) {
        this.organization = organization;
        return this;
    }

    public UserDTO getAgent() {
        return agent;
    }

    public ActivityDTO setAgent(UserDTO agent) {
        this.agent = agent;
        return this;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public ActivityDTO setLocation(LocationDTO location) {
        this.location = location;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public ActivityDTO setDone(boolean done) {
        this.done = done;
        return this;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public ActivityDTO setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityDTO)) return false;
        ActivityDTO that = (ActivityDTO) o;
        return done == that.done &&
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            type == that.type &&
            Objects.equals(deal, that.deal) &&
            Objects.equals(person, that.person) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(agent, that.agent) &&
            Objects.equals(location, that.location) &&
            Objects.equals(closedDate, that.closedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, memo, start, end, type, deal, person, organization, agent, location, done, closedDate);
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
            ", deal=" + deal +
            ", person=" + person +
            ", organization=" + organization +
            ", agent=" + agent +
            ", location=" + location +
            ", done=" + done +
            ", closedDate=" + closedDate +
            '}';
    }
}
