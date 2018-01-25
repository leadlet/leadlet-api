package com.leadlet.domain;

import com.leadlet.domain.enumeration.TimelineItemType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline)) return false;

        Timeline timeline = (Timeline) o;

        if (!id.equals(timeline.id)) return false;
        if (type != timeline.type) return false;
        if (!sourceId.equals(timeline.sourceId)) return false;
        if (person != null ? !person.equals(timeline.person) : timeline.person != null) return false;
        if (organization != null ? !organization.equals(timeline.organization) : timeline.organization != null)
            return false;
        return user != null ? user.equals(timeline.user) : timeline.user == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + sourceId.hashCode();
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + id +
            ", type=" + type +
            ", sourceId=" + sourceId +
            ", person=" + person +
            ", organization=" + organization +
            ", user=" + user +
            '}';
    }
}