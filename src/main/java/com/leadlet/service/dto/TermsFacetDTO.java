package com.leadlet.service.dto;

import java.util.HashMap;
import java.util.Map;

public class TermsFacetDTO extends FacetDTO {

    private Map<String,Long> options = new HashMap<>();

    public Map<String, Long> getOptions() {
        return options;
    }

    public TermsFacetDTO setOptions(Map<String, Long> options) {
        this.options = options;
        return this;
    }

    public void addTerm(String key, long count) {

        options.put(key,count);

    }

}
