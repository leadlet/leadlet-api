package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityDTO implements Serializable {
    private static final long serialVersionUID = -8156768639935476501L;

    private String name;

    public String getName() {
        return name;
    }

    public AuthorityDTO() {
    }

    public AuthorityDTO(String name) {
        this.name = name;
    }

    public AuthorityDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorityDTO)) return false;
        AuthorityDTO that = (AuthorityDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
