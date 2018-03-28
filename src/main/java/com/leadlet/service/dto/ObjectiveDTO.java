package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.leadlet.domain.enumeration.ActivityType;

/**
 * A DTO for the Objective entity.
 */
public class ObjectiveDTO implements Serializable {

    private Long id;

    private ActivityType name;

    private Long dailyAmount;

    private Long weeklyAmount;

    private Long monthlyAmount;

    private Long userId;

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

    public Long getDailyAmount() {
        return dailyAmount;
    }

    public void setDailyAmount(Long dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    public Long getWeeklyAmount() {
        return weeklyAmount;
    }

    public void setWeeklyAmount(Long weeklyAmount) {
        this.weeklyAmount = weeklyAmount;
    }

    public Long getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Long monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
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
            Objects.equals(dailyAmount, that.dailyAmount) &&
            Objects.equals(weeklyAmount, that.weeklyAmount) &&
            Objects.equals(monthlyAmount, that.monthlyAmount) &&
            Objects.equals(userId, that.userId);
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
            ", dailyAmount=" + dailyAmount +
            ", weeklyAmount=" + weeklyAmount +
            ", monthlyAmount=" + monthlyAmount +
            ", userId=" + userId +
            '}';
    }
}
