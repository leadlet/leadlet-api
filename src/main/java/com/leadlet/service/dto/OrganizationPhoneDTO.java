package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.PhoneType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ContactPhone entity.
 */
public class OrganizationPhoneDTO implements Serializable {

    private Long id;

    private String phone;

    private PhoneType type;

    private Long organizationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPhoneDTO)) return false;
        OrganizationPhoneDTO that = (OrganizationPhoneDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            type == that.type &&
            Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, phone, type, organizationId);
    }

    @Override
    public String toString() {
        return "OrganizationPhoneDTO{" +
            "id=" + id +
            ", phone='" + phone + '\'' +
            ", type=" + type +
            ", organizationId=" + organizationId +
            '}';
    }
}
