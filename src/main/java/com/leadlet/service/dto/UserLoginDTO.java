package com.leadlet.service.dto;

public class UserLoginDTO extends UserDTO{
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public UserLoginDTO setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
