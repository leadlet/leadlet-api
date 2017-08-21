package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.leadlet.domain.enumeration.EmailType;

/**
 * A DTO for the ContactEmail entity.
 */
public class ContactEmailDTO implements Serializable {

    private Long id;

    private String email;

    private EmailType type;

    private Long contactId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactEmailDTO contactEmailDTO = (ContactEmailDTO) o;
        if(contactEmailDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactEmailDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactEmailDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
