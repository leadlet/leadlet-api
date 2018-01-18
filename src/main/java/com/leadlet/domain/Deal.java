package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Deal.
 */
@Entity
@Table(name = "deal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Deal extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "potential_value")
    private Double potentialValue;

    @ManyToOne
    private Stage stage;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deal)) return false;
        Deal deal = (Deal) o;
        return Objects.equals(id, deal.id) &&
            Objects.equals(name, deal.name) &&
            Objects.equals(order, deal.order) &&
            Objects.equals(potentialValue, deal.potentialValue) &&
            Objects.equals(stage, deal.stage) &&
            Objects.equals(person, deal.person) &&
            Objects.equals(organization, deal.organization) &&
            Objects.equals(user, deal.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, order, potentialValue, stage, person, organization, user);
    }

    @Override
    public String toString() {
        return "Deal{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", order=" + order +
            ", potentialValue=" + potentialValue +
            ", stage=" + stage +
            ", person=" + person +
            ", organization=" + organization +
            ", user=" + user +
            '}';
    }
}
