package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.FacetType;

import java.io.Serializable;

public class FacetDefinitionDTO implements Serializable {

    private String id;

    private FacetType type;

    private String dataField;

    public String getId() {
        return id;
    }

    public FacetDefinitionDTO setId(String id) {
        this.id = id;
        return this;
    }

    public FacetType getType() {
        return type;
    }

    public FacetDefinitionDTO setType(FacetType type) {
        this.type = type;
        return this;
    }

    public String getDataField() {
        return dataField;
    }

    public FacetDefinitionDTO setDataField(String dataField) {
        this.dataField = dataField;
        return this;
    }
}
