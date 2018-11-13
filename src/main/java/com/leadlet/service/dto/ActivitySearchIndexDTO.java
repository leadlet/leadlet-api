package com.leadlet.service.dto;


import com.leadlet.domain.Activity;
import com.leadlet.domain.ActivityType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the Deal entity.
 */
public class ActivitySearchIndexDTO implements Serializable {

    private Long id;
    private Date createdDate;
    private Date startDate;
    private ActivityType activityType;
    private String title;
    private Long personId;
    private Long dealId;
    private Long agentId;
    private Long appAccountId;

    private boolean isDone;

    public ActivitySearchIndexDTO(){

    }

    public ActivitySearchIndexDTO(Activity activity){

        this.id = activity.getId();
        this.createdDate = new Date(activity.getCreatedDate().toEpochMilli());
        this.startDate = new Date(activity.getStart().toEpochMilli());
        this.activityType = activity.getActivityType();
        this.title = activity.getTitle();
        this.isDone = activity.isDone();
        this.personId = activity.getPerson() == null ? null : activity.getPerson().getId() ;
        this.dealId = activity.getDeal() == null ? null : activity.getDeal().getId() ;
        this.agentId = activity.getAgent() == null ? null : activity.getAgent().getId() ;
        this.appAccountId = activity.getAppAccount().getId();

    }

    public Long getId() {
        return id;
    }

    public ActivitySearchIndexDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public ActivitySearchIndexDTO setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ActivitySearchIndexDTO setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ActivitySearchIndexDTO setActivityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ActivitySearchIndexDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isDone() {
        return isDone;
    }

    public ActivitySearchIndexDTO setDone(boolean done) {
        isDone = done;
        return this;
    }

    public Long getPersonId() {
        return personId;
    }

    public ActivitySearchIndexDTO setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Long getDealId() {
        return dealId;
    }

    public ActivitySearchIndexDTO setDealId(Long dealId) {
        this.dealId = dealId;
        return this;
    }

    public Long getAgentId() {
        return agentId;
    }

    public ActivitySearchIndexDTO setAgentId(Long agentId) {
        this.agentId = agentId;
        return this;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public ActivitySearchIndexDTO setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
        return this;
    }

    public XContentBuilder getBuilder() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", getId());
            builder.timeField("created_date", getCreatedDate());
            builder.field("start_date", getStartDate());
            builder.field("activity_type", getActivityType().getName());
            builder.field("title", getTitle());
            builder.field("is_done", isDone());
            builder.field("person_id", getPersonId());
            builder.field("deal_id", getDealId());
            builder.field("agent_id", getAgentId());
            builder.field("app_account_id", getAppAccountId());

        }
        builder.endObject();

        return builder;
    }
}


