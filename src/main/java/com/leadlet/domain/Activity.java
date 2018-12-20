package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity extends AbstractSearchableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 64)
    @NotNull
    private String title;

    @Column(name = "memo")
    private String memo;

    @Column(name = "start", nullable = false)
    @NotNull
    private Instant start;

    @Column(name = "end", nullable = false)
    @NotNull
    private Instant end;

    @OneToOne
    @NotNull
    private ActivityType activityType;

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private Contact contact;

    @ManyToOne
    private User agent;

    private Location location;

    @NotNull
    @Column(nullable = false)
    private boolean done = false;

    @Column(name = "closed_date")
    private Instant closedDate = null;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getAgent() {
        return agent;
    }

    public Activity setAgent(User agent) {
        this.agent = agent;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isDone() {
        return done;
    }

    public Activity setDone(boolean done) {
        this.done = done;
        return this;
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
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return done == activity.done &&
            Objects.equals(id, activity.id) ;
    }

    @Override
    public int hashCode() {

        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", memo='" + memo + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", activityType=" + activityType +
            ", location=" + location +
            ", done=" + done +
            ", closedDate=" + closedDate +
            '}';
    }
}
