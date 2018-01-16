package com.leadlet.service.dto;


import com.leadlet.domain.Organization;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Organization entity.
 */
public class OrganizationDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private List<OrganizationPhoneDTO> phones;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrganizationPhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<OrganizationPhoneDTO> phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationDTO)) return false;
        OrganizationDTO that = (OrganizationDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phones, that.phones) &&
            Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, phones, email);
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            '}';
    }
}
