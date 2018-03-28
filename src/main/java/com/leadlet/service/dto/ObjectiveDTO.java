package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.leadlet.domain.enumeration.ActivityType;
import com.leadlet.domain.enumeration.PeriodType;

/**
 * A DTO for the Objective entity.
 */
public class ObjectiveDTO implements Serializable {

    private Long id;

    private ActivityType name;

    private Long amount;

    private Long userId;

    private PeriodType period;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityType getName() {
        return name;
    }

    public void setName(ActivityType name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public PeriodType getPeriod() {
        return period;
    }

    public void setPeriod(PeriodType period) {
        this.period = period;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectiveDTO)) return false;
        ObjectiveDTO that = (ObjectiveDTO) o;
        return Objects.equals(id, that.id) &&
            name == that.name &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(userId, that.userId) &&
            period == that.period;
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObjectiveDTO{" +
            "id=" + id +
            ", name=" + name +
            ", amount=" + amount +
            ", userId=" + userId +
            ", period=" + period +
            '}';
    }
}
