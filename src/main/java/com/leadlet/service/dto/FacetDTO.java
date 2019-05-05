package com.leadlet.service.dto;

import java.io.Serializable;

public class FacetDTO implements Serializable {

    private String id;

    private String field;

    public String getId() {
        return id;
    }

    public FacetDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getField() {
        return field;
    }

    public FacetDTO setField(String field) {
        this.field = field;
        return this;
    }
}
