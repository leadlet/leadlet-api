package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class DocumentDTO implements Serializable {

    private Long id;

    private String name;

    private String url;

    private Long personId;

    private Long organizationId;

    private Long dealId;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentDTO)) return false;
        DocumentDTO that = (DocumentDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(url, that.url) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(dealId, that.dealId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, url, personId, organizationId, dealId);
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", personId=" + personId +
            ", organizationId=" + organizationId +
            ", dealId=" + dealId +
            '}';
    }
}
