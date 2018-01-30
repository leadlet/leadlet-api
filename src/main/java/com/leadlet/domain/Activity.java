package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadlet.domain.enumeration.ActivityType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity extends AbstractAccountSpecificEntity implements Serializable {

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
    private ZonedDateTime start;

    @Column(name = "end", nullable = false)
    @NotNull
    private ZonedDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "type" , nullable = false)
    @NotNull
    private ActivityType type;

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private User user;

    private Location location;

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

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", memo='" + memo + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", type=" + type +
            ", deal=" + deal +
            ", person=" + person +
            ", organization=" + organization +
            ", user=" + user +
            ", location=" + location +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) &&
            Objects.equals(title, activity.title) &&
            Objects.equals(memo, activity.memo) &&
            Objects.equals(start, activity.start) &&
            Objects.equals(end, activity.end) &&
            type == activity.type &&
            Objects.equals(deal, activity.deal) &&
            Objects.equals(person, activity.person) &&
            Objects.equals(organization, activity.organization) &&
            Objects.equals(user, activity.user) &&
            Objects.equals(location, activity.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, memo, start, end, type, deal, person, organization, user, location);
    }
}
