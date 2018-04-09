package com.leadlet.domain;

import com.leadlet.domain.enumeration.ActivityType;

public class ActivityAggregation {
    private ActivityType activityType;
    private Long count;

    public ActivityAggregation(ActivityType activityType, Long count) {
        this.activityType = activityType;
        this.count = count;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ActivityAggregation setActivityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public ActivityAggregation setCount(Long count) {
        this.count = count;
        return this;
    }
}
