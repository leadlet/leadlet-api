package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class ChannelDTO implements Serializable {

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
        if (!(o instanceof ChannelDTO)) return false;
        ChannelDTO that = (ChannelDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ChannelDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
