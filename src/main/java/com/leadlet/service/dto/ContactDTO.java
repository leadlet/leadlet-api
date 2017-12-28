package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

import com.leadlet.domain.Contact;
import com.leadlet.domain.ContactPhone;
import com.leadlet.domain.enumeration.ContactType;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

    private String title;

    private ContactType type;

    private Boolean isContactPerson;

    private Long organizationId;

    private List<ContactPhoneDTO> phones;

    private String email;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public Boolean isIsContactPerson() {
        return isContactPerson;
    }

    public void setIsContactPerson(Boolean isContactPerson) {
        this.isContactPerson = isContactPerson;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContactPhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactPhoneDTO> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if(contactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", location='" + location + '\'' +
            ", title='" + title + '\'' +
            ", type=" + type +
            ", isContactPerson=" + isContactPerson +
            ", organizationId=" + organizationId +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            '}';
    }
}
