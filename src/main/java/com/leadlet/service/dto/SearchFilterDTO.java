package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.SearchFilterType;

import java.io.Serializable;

public class SearchFilterDTO implements Serializable {

    private String dataField;
    private SearchFilterType operator;
    private Object values;

    public String getDataField() {
        return dataField;
    }

    public SearchFilterDTO setDataField(String dataField) {
        this.dataField = dataField;
        return this;
    }

    public SearchFilterType getOperator() {
        return operator;
    }

    public SearchFilterDTO setOperator(SearchFilterType operator) {
        this.operator = operator;
        return this;
    }

    public Object getValues() {
        return values;
    }

    public SearchFilterDTO setValues(Object values) {
        this.values = values;
        return this;
    }
}
