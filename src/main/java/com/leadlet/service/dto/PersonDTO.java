package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Person entity.
 */
public class PersonDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String title;

    private Long organizationId;

    private String organizationName;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public List<ContactPhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactPhoneDTO> phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) &&
            Objects.equals(name, personDTO.name) &&
            Objects.equals(address, personDTO.address) &&
            Objects.equals(title, personDTO.title) &&
            Objects.equals(organizationId, personDTO.organizationId) &&
            Objects.equals(phones, personDTO.phones) &&
            Objects.equals(email, personDTO.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, title, organizationId, phones, email);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", title='" + title + '\'' +
            ", organizationId=" + organizationId +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            '}';
    }
}
