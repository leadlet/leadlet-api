package com.leadlet.service.dto;

import com.leadlet.domain.Authority;
import com.leadlet.domain.User;
import com.leadlet.service.UserService;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class LoginResponseUserDTO  extends UserDTO{

    private String jwt;


    public LoginResponseUserDTO(User user, String jwt){
        super(user);
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginResponseUserDTO{" +
            "jwt='" + jwt + '\'' +
            '}';
    }
}
