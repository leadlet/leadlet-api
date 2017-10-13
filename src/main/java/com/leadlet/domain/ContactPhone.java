package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.leadlet.domain.enumeration.PhoneType;

/**
 * A ContactPhone.
 */
@Entity
@Table(name = "contact_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactPhone extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PhoneType type;

    @ManyToOne
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public ContactPhone phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PhoneType getType() {
        return type;
    }

    public ContactPhone type(PhoneType type) {
        this.type = type;
        return this;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public ContactPhone contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactPhone contactPhone = (ContactPhone) o;
        if (contactPhone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactPhone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactPhone{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
