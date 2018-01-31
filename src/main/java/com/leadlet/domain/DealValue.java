package com.leadlet.domain;

import com.leadlet.domain.enumeration.CurrencyType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class DealValue {
    @Column(name = "potential_value")
    private Double potentialValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyType currency;

    public Double getPotentialValue() {
        return potentialValue;
    }

    public DealValue setPotentialValue(Double potentialValue) {
        this.potentialValue = potentialValue;
        return this;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public DealValue setCurrency(CurrencyType currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealValue)) return false;
        DealValue dealValue = (DealValue) o;
        return Objects.equals(potentialValue, dealValue.potentialValue) &&
            currency == dealValue.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(potentialValue, currency);
    }

    @Override
    public String toString() {
        return "DealValue{" +
            "potentialValue=" + potentialValue +
            ", currency=" + currency +
            '}';
    }
}
