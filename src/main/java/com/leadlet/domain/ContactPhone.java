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
public class ContactPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PhoneType type;

    @Column(name="person")
    private Person person;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactPhone)) return false;
        ContactPhone that = (ContactPhone) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            type == that.type &&
            Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, phone, type, person);
    }

    @Override
    public String toString() {
        return "ContactPhone{" +
            "id=" + id +
            ", phone='" + phone + '\'' +
            ", type=" + type +
            ", person=" + person +
            '}';
    }
}
