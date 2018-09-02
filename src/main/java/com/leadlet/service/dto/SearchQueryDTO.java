package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.QueryOperatorType;
import com.leadlet.domain.enumeration.SearchFilterType;

import java.io.Serializable;
import java.util.Set;

public class SearchQueryDTO implements Serializable {

    private QueryOperatorType operator;
    private Set<SearchFilterDTO> filters;

    public QueryOperatorType getOperator() {
        return operator;
    }

    public SearchQueryDTO setOperator(QueryOperatorType operator) {
        this.operator = operator;
        return this;
    }

    public Set<SearchFilterDTO> getFilters() {
        return filters;
    }

    public SearchQueryDTO setFilters(Set<SearchFilterDTO> filters) {
        this.filters = filters;
        return this;
    }
}
