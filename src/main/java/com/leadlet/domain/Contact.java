package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.leadlet.domain.enumeration.ContactType;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ContactType type;

    @Column(name = "is_contact_person")
    private Boolean isContactPerson;

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactPhone> phones = new HashSet<>();

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactEmail> emails = new HashSet<>();

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    private Contact organization;

    @ManyToOne
    private AppAccount appAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public Contact location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContactType getType() {
        return type;
    }

    public Contact type(ContactType type) {
        this.type = type;
        return this;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public Boolean isIsContactPerson() {
        return isContactPerson;
    }

    public Contact isContactPerson(Boolean isContactPerson) {
        this.isContactPerson = isContactPerson;
        return this;
    }

    public void setIsContactPerson(Boolean isContactPerson) {
        this.isContactPerson = isContactPerson;
    }

    public Set<ContactPhone> getPhones() {
        return phones;
    }

    public Contact phones(Set<ContactPhone> contactPhones) {
        this.phones = contactPhones;
        return this;
    }

    public Contact addPhone(ContactPhone contactPhone) {
        this.phones.add(contactPhone);
        contactPhone.setContact(this);
        return this;
    }

    public Contact removePhone(ContactPhone contactPhone) {
        this.phones.remove(contactPhone);
        contactPhone.setContact(null);
        return this;
    }

    public void setPhones(Set<ContactPhone> contactPhones) {
        this.phones = contactPhones;
    }

    public Set<ContactEmail> getEmails() {
        return emails;
    }

    public Contact emails(Set<ContactEmail> contactEmails) {
        this.emails = contactEmails;
        return this;
    }

    public Contact addEmail(ContactEmail contactEmail) {
        this.emails.add(contactEmail);
        contactEmail.setContact(this);
        return this;
    }

    public Contact removeEmail(ContactEmail contactEmail) {
        this.emails.remove(contactEmail);
        contactEmail.setContact(null);
        return this;
    }

    public void setEmails(Set<ContactEmail> contactEmails) {
        this.emails = contactEmails;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Contact documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Contact addDocument(Document document) {
        this.documents.add(document);
        document.setContact(this);
        return this;
    }

    public Contact removeDocument(Document document) {
        this.documents.remove(document);
        document.setContact(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Contact getOrganization() {
        return organization;
    }

    public Contact organization(Contact contact) {
        this.organization = contact;
        return this;
    }

    public void setOrganization(Contact contact) {
        this.organization = contact;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", type='" + getType() + "'" +
            ", isContactPerson='" + isIsContactPerson() + "'" +
            "}";
    }
}
