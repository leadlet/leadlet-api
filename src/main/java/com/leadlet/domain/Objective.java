package com.leadlet.domain;

import com.leadlet.domain.enumeration.ActivityType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Objective.
 */
@Entity
@Table(name = "objective")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Objective extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ActivityType name;

    @Column(name = "dailyAmount")
    private Long dailyAmount;

    @Column(name = "weeklyAmount")
    private Long weeklyAmount;

    @Column(name = "monthlyAmount")
    private Long monthlyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppAccount appAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    @Override
    public AppAccount getAppAccount() {
        return appAccount;
    }

    @Override
    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objective)) return false;
        Objective objective = (Objective) o;
        return Objects.equals(id, objective.id) &&
            name == objective.name &&
            Objects.equals(dailyAmount, objective.dailyAmount) &&
            Objects.equals(weeklyAmount, objective.weeklyAmount) &&
            Objects.equals(monthlyAmount, objective.monthlyAmount) &&
            Objects.equals(appAccount, objective.appAccount) &&
            Objects.equals(user, objective.user);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Objective{" +
            "id=" + id +
            ", name=" + name +
            ", dailyAmount=" + dailyAmount +
            ", weeklyAmount=" + weeklyAmount +
            ", monthlyAmount=" + monthlyAmount +
            ", appAccount=" + appAccount +
            ", user=" + user +
            '}';
    }
}
