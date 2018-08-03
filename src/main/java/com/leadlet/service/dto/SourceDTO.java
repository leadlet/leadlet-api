package com.leadlet.service.dto;

import com.leadlet.domain.Deal;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class SourceDTO implements Serializable {

    private Long id;

    private String name;

    private Set<Deal> deals;

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

    public Set<Deal> getDeals() {
        return deals;
    }

    public void setDeals(Set<Deal> deals) {
        this.deals = deals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceDTO)) return false;
        SourceDTO sourceDTO = (SourceDTO) o;
        return Objects.equals(id, sourceDTO.id) &&
            Objects.equals(name, sourceDTO.name) &&
            Objects.equals(deals, sourceDTO.deals);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, deals);
    }

    @Override
    public String toString() {
        return "SourceDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", deals=" + deals +
            '}';
    }
}
