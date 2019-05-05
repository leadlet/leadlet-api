package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.FacetType;

import java.io.Serializable;

public class FilterDefinitionDTO implements Serializable {

    private String id;

    private FacetType type;

    private String field;

    public String getId() {
        return id;
    }

    public FilterDefinitionDTO setId(String id) {
        this.id = id;
        return this;
    }

    public FacetType getType() {
        return type;
    }

    public FilterDefinitionDTO setType(FacetType type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public FilterDefinitionDTO setField(String field) {
        this.field = field;
        return this;
    }

    @Override
    public String toString() {
        return "FilterDefinitionDTO{" +
            "id='" + id + '\'' +
            ", type=" + type +
            ", field='" + field + '\'' +
            '}';
    }
}
