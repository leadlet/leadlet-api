package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A Deal.
 */
@Entity
@Table(name = "deal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Deal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "priority")
    private Integer priority;

    DealValue dealValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Stage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "possible_close_date")
    private ZonedDateTime possibleCloseDate;

    @OneToMany(mappedBy = "deal", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Activity> activities;

    @OneToMany(mappedBy = "deal", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Timeline> timelines;

    @OneToMany(mappedBy = "deal", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Note> notes;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Deal setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public Deal setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Deal setPossibleCloseDate(ZonedDateTime possibleCloseDate) {
        this.possibleCloseDate = possibleCloseDate;
        return this;
    }

    public ZonedDateTime getPossibleCloseDate() {
        return possibleCloseDate;
    }

    public DealValue getDealValue() {
        return dealValue;
    }

    public Deal setDealValue(DealValue dealValue) {
        this.dealValue = dealValue;
        return this;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Deal setActivities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Set<Timeline> getTimelines() {
        return timelines;
    }

    public Deal setTimelines(Set<Timeline> timelines) {
        this.timelines = timelines;
        return this;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Deal setNotes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deal)) return false;
        Deal deal = (Deal) o;
        return Objects.equals(id, deal.id) &&
            Objects.equals(title, deal.title) &&
            Objects.equals(priority, deal.priority) &&
            Objects.equals(dealValue, deal.dealValue) &&
            Objects.equals(stage, deal.stage) &&
            Objects.equals(person, deal.person) &&
            Objects.equals(organization, deal.organization) &&
            Objects.equals(owner, deal.owner) &&
            Objects.equals(possibleCloseDate, deal.possibleCloseDate) &&
            Objects.equals(activities, deal.activities) &&
            Objects.equals(timelines, deal.timelines) &&
            Objects.equals(notes, deal.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, priority, dealValue, stage, person, organization, owner, possibleCloseDate, activities, timelines, notes);
    }

    @Override
    public String toString() {
        return "Deal{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", priority=" + priority +
            ", dealValue=" + dealValue +
            ", stage=" + stage +
            ", person=" + person +
            ", organization=" + organization +
            ", owner=" + owner +
            ", possibleCloseDate=" + possibleCloseDate +
            ", activities=" + activities +
            ", timelines=" + timelines +
            ", notes=" + notes +
            '}';
    }
}
