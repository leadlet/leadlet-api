package com.leadlet.service.dto;

import com.leadlet.domain.User;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * A DTO representing a user, with his/her authorities.
 */
public class UserUpdateDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public UserUpdateDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserUpdateDTO(User user) {
       this.id = user.getId();
       this.firstName = user.getFirstName();
       this.lastName = user.getLastName();
       this.password = user.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserUpdateDTO(Long id, String firstName, String lastName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserUpdateDTO)) return false;
        UserUpdateDTO that = (UserUpdateDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, password);
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
