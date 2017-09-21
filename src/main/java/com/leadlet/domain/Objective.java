package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.leadlet.domain.enumeration.ObjectiveSourceType;

/**
 * A Objective.
 */
@Entity
@Table(name = "objective")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Objective implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_id")
    private Long sourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private ObjectiveSourceType sourceType;

    @Column(name = "name")
    private String name;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @Column(name = "target_amount")
    private Long targetAmount;

    @Column(name = "current_amount")
    private Long currentAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public Objective sourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public ObjectiveSourceType getSourceType() {
        return sourceType;
    }

    public Objective sourceType(ObjectiveSourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public void setSourceType(ObjectiveSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getName() {
        return name;
    }

    public Objective name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Objective dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getTargetAmount() {
        return targetAmount;
    }

    public Objective targetAmount(Long targetAmount) {
        this.targetAmount = targetAmount;
        return this;
    }

    public void setTargetAmount(Long targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public Objective currentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
        return this;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Objective objective = (Objective) o;
        if (objective.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), objective.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Objective{" +
            "id=" + getId() +
            ", sourceId='" + getSourceId() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            ", name='" + getName() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", targetAmount='" + getTargetAmount() + "'" +
            ", currentAmount='" + getCurrentAmount() + "'" +
            "}";
    }
}
