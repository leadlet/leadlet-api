package com.leadlet.service.dto;


import com.leadlet.domain.Timeline;
import com.leadlet.domain.enumeration.TimelineItemType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the Deal entity.
 */
public class TimelineSearchIndexDTO implements Serializable {

    private Long id;
    private Date createdDate;
    private Long contactId;
    private Long agentId;
    private Long dealId;
    private Long appAccountId;
    private TimelineItemType type;

    public TimelineSearchIndexDTO(){

    }

    public TimelineSearchIndexDTO(Timeline timeline){

        this.id = timeline.getId();
        this.createdDate = new Date(timeline.getCreatedDate().toEpochMilli());
        if(timeline.getContact() != null){
            this.contactId = timeline.getContact().getId();
        }
        if(timeline.getAgent() != null){
            this.agentId = timeline.getAgent().getId();
        }
        if(timeline.getDeal() != null){
            this.dealId = timeline.getDeal().getId();
        }
        this.type = timeline.getType();
        this.appAccountId = timeline.getAppAccount().getId();

    }

    public Long getId() {
        return id;
    }

    public TimelineSearchIndexDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public TimelineSearchIndexDTO setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Long getcontactId() {
        return contactId;
    }

    public TimelineSearchIndexDTO setContactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }

    public Long getAgentId() {
        return agentId;
    }

    public TimelineSearchIndexDTO setAgentId(Long agentId) {
        this.agentId = agentId;
        return this;
    }

    public Long getDealId() {
        return dealId;
    }

    public TimelineSearchIndexDTO setDealId(Long dealId) {
        this.dealId = dealId;
        return this;
    }

    public TimelineItemType getType() {
        return type;
    }

    public TimelineSearchIndexDTO setType(TimelineItemType type) {
        this.type = type;
        return this;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public TimelineSearchIndexDTO setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
        return this;
    }

    public XContentBuilder getBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", getId());
            builder.timeField("created_date", getCreatedDate());
            builder.field("contact_id", getcontactId());
            builder.field("agent_id", getAgentId());
            builder.field("deal_id", getDealId());
            builder.field("type", getType());
            builder.field("app_account_id", getAppAccountId());

        }
        builder.endObject();

        return builder;
    }
}


