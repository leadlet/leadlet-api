package com.leadlet.domain;

import com.leadlet.domain.enumeration.CurrencyType;
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

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "potential_value")
    private Double potentialValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyType currency;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public Deal setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Double getPotentialValue() {
        return potentialValue;
    }

    public void setPotentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
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

    public Deal setCurrency(CurrencyType currency) {
        this.currency = currency;
        return this;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deal)) return false;
        Deal deal = (Deal) o;
        return Objects.equals(id, deal.id) &&
            Objects.equals(name, deal.name) &&
            Objects.equals(priority, deal.priority) &&
            Objects.equals(potentialValue, deal.potentialValue) &&
            Objects.equals(stage, deal.stage) &&
            Objects.equals(person, deal.person) &&
            Objects.equals(organization, deal.organization) &&
            Objects.equals(owner, deal.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, priority, potentialValue, stage, person, organization, owner);
    }

    @Override
    public String toString() {
        return "Deal{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", priority=" + priority +
            ", potentialValue=" + potentialValue +
            ", stage=" + stage +
            ", person=" + person +
            ", organization=" + organization +
            ", owner=" + owner +
            '}';
    }
}
