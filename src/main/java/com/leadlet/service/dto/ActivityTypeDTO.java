package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.Objects;

public class ActivityTypeDTO implements Serializable {

    private Long id;

    private String name;

    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityTypeDTO)) return false;
        ActivityTypeDTO that = (ActivityTypeDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, icon);
    }

    @Override
    public String toString() {
        return "ActivityTypeDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", icon='" + icon + '\'' +
            '}';
    }
}
