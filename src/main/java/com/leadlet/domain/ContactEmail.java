package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.leadlet.domain.enumeration.EmailType;

/**
 * A ContactEmail.
 */
@Entity
@Table(name = "contact_email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactEmail extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private EmailType type;

    @ManyToOne
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public ContactEmail email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getType() {
        return type;
    }

    public ContactEmail type(EmailType type) {
        this.type = type;
        return this;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public ContactEmail contact(Contact contact) {
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
        ContactEmail contactEmail = (ContactEmail) o;
        if (contactEmail.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactEmail.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactEmail{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
