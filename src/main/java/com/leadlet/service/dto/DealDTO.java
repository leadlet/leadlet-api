package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealDTO implements Serializable {

    private Long id;

    private String name;

    private Integer order;

    private Double potentialValue;

    private Long stageId;

    private Long personId;

    private Long organizationId;

    private Long appAccountId;

    private Long userId;

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

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long contactId) {
        this.personId = contactId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long contactId) {
        this.organizationId = contactId;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public void setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long appUserId) {
        this.userId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DealDTO dealDTO = (DealDTO) o;
        if(dealDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dealDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DealDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order='" + getOrder() + "'" +
            ", potentialValue='" + getPotentialValue() + "'" +
            "}";
    }
}
