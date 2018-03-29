package com.leadlet.service.dto;

import com.leadlet.domain.enumeration.ActivityType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Objective entity.
 */
public class TeamObjectiveDTO implements Serializable {

    private ActivityType name;

    private long dailyAmount;

    private long weeklyAmount;

    private long monthlyAmount;

    private Long teamId;

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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamObjectiveDTO)) return false;
        TeamObjectiveDTO that = (TeamObjectiveDTO) o;
        return name == that.name &&
            Objects.equals(dailyAmount, that.dailyAmount) &&
            Objects.equals(weeklyAmount, that.weeklyAmount) &&
            Objects.equals(monthlyAmount, that.monthlyAmount) &&
            Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, dailyAmount, weeklyAmount, monthlyAmount, teamId);
    }

    @Override
    public String toString() {
        return "TeamObjectiveDTO{" +
            "name=" + name +
            ", dailyAmount=" + dailyAmount +
            ", weeklyAmount=" + weeklyAmount +
            ", monthlyAmount=" + monthlyAmount +
            ", teamId=" + teamId +
            '}';
    }
}
