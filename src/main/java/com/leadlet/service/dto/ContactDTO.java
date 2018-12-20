package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.Gender;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String login;

    private Gender gender;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactDTO)) return false;
        ContactDTO contactDTO = (ContactDTO) o;
        return Objects.equals(id, contactDTO.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", login='" + login + '\'' +
            ", gender='" + gender + '\'' +
            ", phones=" + phones +
            ", email='" + email + '\'' +
            '}';
    }
}
