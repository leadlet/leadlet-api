package com.leadlet.service.dto;

import java.io.Serializable;

public class FacetDTO implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public FacetDTO setId(String id) {
        this.id = id;
        return this;
    }
}
