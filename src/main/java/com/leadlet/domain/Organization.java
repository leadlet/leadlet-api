package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrganizationPhone> phones = new HashSet<>();

    @Column(name = "email")
    @Email
    private String email;

    @ManyToOne
    private AppAccount appAccount;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Activity> activities;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Deal> deals;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Timeline> timelines;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Person> persons;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<OrganizationPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<OrganizationPhone> phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phones, that.phones) &&
            Objects.equals(email, that.email) &&
            Objects.equals(appAccount, that.appAccount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, phones, email, appAccount);
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            ", appAccount=" + appAccount +
            '}';
    }
}
