package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    private DocumentStorageInfo documentStorageInfo;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private Deal deal;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public Document setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    public DocumentStorageInfo getDocumentStorageInfo() {
        return documentStorageInfo;
    }

    public Document setDocumentStorageInfo(DocumentStorageInfo documentStorageInfo) {
        this.documentStorageInfo = documentStorageInfo;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public Document setPerson(Person person) {
        this.person = person;
        return this;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Document setOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public Deal getDeal() {
        return deal;
    }

    public Document setDeal(Deal deal) {
        this.deal = deal;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
            Objects.equals(name, document.name) &&
            Objects.equals(documentStorageInfo, document.documentStorageInfo) &&
            Objects.equals(person, document.person) &&
            Objects.equals(organization, document.organization) &&
            Objects.equals(deal, document.deal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
