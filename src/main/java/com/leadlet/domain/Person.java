package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.Contact;
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
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactPhone> phones = new HashSet<>();

    @Column(name = "email")
    @Email
    private String email;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private AppAccount appAccount;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ContactPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<ContactPhone> phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
            Objects.equals(name, person.name) &&
            Objects.equals(address, person.address) &&
            Objects.equals(title, person.title) &&
            Objects.equals(phones, person.phones) &&
            Objects.equals(email, person.email) &&
            Objects.equals(organization, person.organization) &&
            Objects.equals(appAccount, person.appAccount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, title, phones, email, organization, appAccount);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", title='" + title + '\'' +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            ", organization=" + organization +
            ", appAccount=" + appAccount +
            '}';
    }
}
