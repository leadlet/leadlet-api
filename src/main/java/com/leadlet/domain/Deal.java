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
    private Contact person;

    @ManyToOne
    private Contact organization;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Deal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public Deal order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Double getPotentialValue() {
        return potentialValue;
    }

    public Deal potentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
        return this;
    }

    public void setPotentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
    }

    public Stage getStage() {
        return stage;
    }

    public Deal stage(Stage stage) {
        this.stage = stage;
        return this;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Contact getPerson() {
        return person;
    }

    public Deal person(Contact contact) {
        this.person = contact;
        return this;
    }

    public void setPerson(Contact contact) {
        this.person = contact;
    }

    public Contact getOrganization() {
        return organization;
    }

    public Deal organization(Contact contact) {
        this.organization = contact;
        return this;
    }

    public void setOrganization(Contact contact) {
        this.organization = contact;
    }

    public User getUser() {
        return user;
    }

    public Deal user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deal deal = (Deal) o;
        if (deal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Deal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order='" + getOrder() + "'" +
            ", potentialValue='" + getPotentialValue() + "'" +
            "}";
    }
}
