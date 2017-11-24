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

    @Column(name = "title")
    private String title;

    @Column(name = "memo")
    private String memo;

    @Column(name = "start")
    private ZonedDateTime start;

    @Column(name = "end")
    private ZonedDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "type" , nullable = false)
    @NotNull
    private ActivityType type;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private Contact person;

    @ManyToOne
    private Contact organization;

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

    public void setEnd(ZonedDateTime start) {
        this.end = start;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Contact getPerson() {
        return person;
    }

    public void setPerson(Contact person) {
        this.person = person;
    }

    public Contact getOrganization() {
        return organization;
    }

    public void setOrganization(Contact organization) {
        this.organization = organization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
            ", documents=" + documents +
            ", deal=" + deal +
            ", person=" + person +
            ", organization=" + organization +
            ", user=" + user +
            '}';
    }
}
