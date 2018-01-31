package com.leadlet.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealValueDTO implements Serializable {

    private String currency;

    private Double potentialValue;

    public String getCurrency() {
        return currency;
    }

    public DealValueDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Double getPotentialValue() {
        return potentialValue;
    }

    public DealValueDTO setPotentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealValueDTO)) return false;
        DealValueDTO that = (DealValueDTO) o;
        return Objects.equals(currency, that.currency) &&
            Objects.equals(potentialValue, that.potentialValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, potentialValue);
    }

    @Override
    public String toString() {
        return "DealValueDTO{" +
            "currency='" + currency + '\'' +
            ", potentialValue=" + potentialValue +
            '}';
    }
}
