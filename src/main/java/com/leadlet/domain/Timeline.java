package com.leadlet.domain;

import com.leadlet.domain.enumeration.TimelineItemType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Timeline.
 */
@Entity
@Table(name = "timeline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timeline extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TimelineItemType type;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Person getPerson() {
        return person;
    }

    public Timeline setPerson(Person person) {
        this.person = person;
        return this;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Timeline setOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline)) return false;
        Timeline timeline = (Timeline) o;
        return Objects.equals(id, timeline.id) &&
            type == timeline.type &&
            Objects.equals(sourceId, timeline.sourceId) &&
            Objects.equals(person, timeline.person) &&
            Objects.equals(organization, timeline.organization) &&
            Objects.equals(deal, timeline.deal) &&
            Objects.equals(user, timeline.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, sourceId, person, organization, deal, user);
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + id +
            ", type=" + type +
            ", sourceId=" + sourceId +
            ", person=" + person +
            ", organization=" + organization +
            ", deal=" + deal +
            ", user=" + user +
            '}';
    }
}
