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

    @Size(max = 100)
    @Column(name = "url", length = 100)
    private String url;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
            Objects.equals(name, document.name) &&
            Objects.equals(url, document.url) &&
            Objects.equals(person, document.person) &&
            Objects.equals(organization, document.organization) &&
            Objects.equals(deal, document.deal);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}
