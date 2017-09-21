package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
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
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "memo")
    private String memo;

    @Column(name = "potential_value")
    private Double potentialValue;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

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
    private AppAccount appAccount;

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

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public Activity order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getMemo() {
        return memo;
    }

    public Activity memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getPotentialValue() {
        return potentialValue;
    }

    public Activity potentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
        return this;
    }

    public void setPotentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Activity startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Activity endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Activity documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Activity addDocument(Document document) {
        this.documents.add(document);
        document.setActivity(this);
        return this;
    }

    public Activity removeDocument(Document document) {
        this.documents.remove(document);
        document.setActivity(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Deal getDeal() {
        return deal;
    }

    public Activity deal(Deal deal) {
        this.deal = deal;
        return this;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Contact getPerson() {
        return person;
    }

    public Activity person(Contact contact) {
        this.person = contact;
        return this;
    }

    public void setPerson(Contact contact) {
        this.person = contact;
    }

    public Contact getOrganization() {
        return organization;
    }

    public Activity organization(Contact contact) {
        this.organization = contact;
        return this;
    }

    public void setOrganization(Contact contact) {
        this.organization = contact;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public Activity appAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
        return this;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    public User getUser() {
        return user;
    }

    public Activity user(User user) {
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order='" + getOrder() + "'" +
            ", memo='" + getMemo() + "'" +
            ", potentialValue='" + getPotentialValue() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
