package com.leadlet.service.dto;

public class RangeFacetDTO extends FacetDTO {

    private Double min;
    private Double max;

    public Double getMin() {
        return min;
    }

    public RangeFacetDTO setMin(Double min) {
        this.min = min;
        return this;
    }

    public Double getMax() {
        return max;
    }

    public RangeFacetDTO setMax(Double max) {
        this.max = max;
        return this;
    }
}
