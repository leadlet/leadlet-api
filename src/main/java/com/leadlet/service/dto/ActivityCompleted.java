package com.leadlet.service.dto;

import com.leadlet.domain.ActivityType;

public class ActivityCompleted {

    ActivityType type;
    long count;

    public ActivityCompleted(ActivityType type, long count) {
        this.type = type;
        this.count = count;
    }

    public ActivityType getType() {
        return type;
    }

    public ActivityCompleted setType(ActivityType type) {
        this.type = type;
        return this;
    }

    public long getCount() {
        return count;
    }

    public ActivityCompleted setCount(long count) {
        this.count = count;
        return this;
    }
}
