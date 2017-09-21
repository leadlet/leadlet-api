package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.leadlet.domain.enumeration.PlanName;

/**
 * A DTO for the SubscriptionPlan entity.
 */
public class SubscriptionPlanDTO implements Serializable {

    private Long id;

    private PlanName planName;

    private String allowedFeatures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanName getPlanName() {
        return planName;
    }

    public void setPlanName(PlanName planName) {
        this.planName = planName;
    }

    public String getAllowedFeatures() {
        return allowedFeatures;
    }

    public void setAllowedFeatures(String allowedFeatures) {
        this.allowedFeatures = allowedFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubscriptionPlanDTO subscriptionPlanDTO = (SubscriptionPlanDTO) o;
        if(subscriptionPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriptionPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriptionPlanDTO{" +
            "id=" + getId() +
            ", planName='" + getPlanName() + "'" +
            ", allowedFeatures='" + getAllowedFeatures() + "'" +
            "}";
    }
}
