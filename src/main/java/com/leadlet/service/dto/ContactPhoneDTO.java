package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.leadlet.domain.enumeration.PhoneType;

/**
 * A DTO for the ContactPhone entity.
 */
public class ContactPhoneDTO implements Serializable {

    private Long id;

    private String phone;

    private PhoneType type;

    private Long contactId;

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

        ContactPhoneDTO contactPhoneDTO = (ContactPhoneDTO) o;
        if(contactPhoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactPhoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactPhoneDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
