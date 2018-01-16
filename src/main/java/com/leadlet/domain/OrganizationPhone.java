package com.leadlet.domain;

import com.leadlet.domain.enumeration.PhoneType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OrganizationPhone.
 */
@Entity
@Table(name = "organization_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrganizationPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PhoneType type;

    @Column(name="organization")
    private Organization organization;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPhone)) return false;
        OrganizationPhone that = (OrganizationPhone) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            type == that.type &&
            Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, phone, type, organization);
    }

    @Override
    public String toString() {
        return "OrganizationPhone{" +
            "id=" + id +
            ", phone='" + phone + '\'' +
            ", type=" + type +
            ", organization=" + organization +
            '}';
    }
}
