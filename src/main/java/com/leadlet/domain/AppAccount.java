package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AppAccount.
 */
@Entity
@Table(name = "app_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "appAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanySubscriptionPlan> subscriptionPlans = new HashSet<>();

    @OneToMany(mappedBy = "appAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AppAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public AppAccount address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<CompanySubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public AppAccount subscriptionPlans(Set<CompanySubscriptionPlan> companySubscriptionPlans) {
        this.subscriptionPlans = companySubscriptionPlans;
        return this;
    }

    public AppAccount addSubscriptionPlan(CompanySubscriptionPlan companySubscriptionPlan) {
        this.subscriptionPlans.add(companySubscriptionPlan);
        companySubscriptionPlan.setAppAccount(this);
        return this;
    }

    public AppAccount removeSubscriptionPlan(CompanySubscriptionPlan companySubscriptionPlan) {
        this.subscriptionPlans.remove(companySubscriptionPlan);
        companySubscriptionPlan.setAppAccount(null);
        return this;
    }

    public void setSubscriptionPlans(Set<CompanySubscriptionPlan> companySubscriptionPlans) {
        this.subscriptionPlans = companySubscriptionPlans;
    }

    public Set<User> getUsers() {
        return users;
    }

    public AppAccount users(Set<User> users) {
        this.users = users;
        return this;
    }

    public AppAccount addUser(User user) {
        this.users.add(user);
        user.setAppAccount(this);
        return this;
    }

    public AppAccount removeUser(User user) {
        this.users.remove(user);
        user.setAppAccount(null);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppAccount appAccount = (AppAccount) o;
        if (appAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
