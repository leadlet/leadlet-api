package com.leadlet.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.leadlet.domain.enumeration.ObjectiveSourceType;

/**
 * A DTO for the Objective entity.
 */
public class ObjectiveDTO implements Serializable {

    private Long id;

    private Long sourceId;

    private ObjectiveSourceType sourceType;

    private String name;

    private ZonedDateTime dueDate;

    private Long targetAmount;

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

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public ObjectiveSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(ObjectiveSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Long targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Long getCurrentAmount() {
        return currentAmount;
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

        ObjectiveDTO objectiveDTO = (ObjectiveDTO) o;
        if(objectiveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), objectiveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObjectiveDTO{" +
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
