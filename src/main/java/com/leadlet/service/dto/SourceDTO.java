package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.Objects;

public class SourceDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceDTO)) return false;
        SourceDTO sourceDTO = (SourceDTO) o;
        return Objects.equals(id, sourceDTO.id) &&
            Objects.equals(name, sourceDTO.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "SourceDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
