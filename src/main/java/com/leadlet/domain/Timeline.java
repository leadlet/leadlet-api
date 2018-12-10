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
public class Timeline extends AbstractSearchableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TimelineItemType type;

    @Lob
    @Column(name = "content", nullable = true)
    private String content;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private User agent;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAgent() {
        return agent;
    }

    public Timeline setAgent(User agent) {
        this.agent = agent;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public Timeline setPerson(Person person) {
        this.person = person;
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
        return Objects.equals(id, timeline.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + id +
            ", type=" + type +
            ", content='" + content + '\'' +
            ", person=" + person +
            ", deal=" + deal +
            ", agent=" + agent +
            '}';
    }
}
