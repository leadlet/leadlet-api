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
    private Long personId;
    private Long userId;
    private Long dealId;
    private TimelineItemType type;

    public TimelineSearchIndexDTO(){

    }

    public TimelineSearchIndexDTO(Timeline timeline){

        this.id = timeline.getId();
        this.createdDate = new Date(timeline.getCreatedDate().toEpochMilli());
        if(timeline.getPerson() != null){
            this.personId = timeline.getPerson().getId();
        }
        if(timeline.getUser() != null){
            this.userId = timeline.getUser().getId();
        }
        if(timeline.getDeal() != null){
            this.dealId = timeline.getDeal().getId();
        }
        this.type = timeline.getType();

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

    public Long getPersonId() {
        return personId;
    }

    public TimelineSearchIndexDTO setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public TimelineSearchIndexDTO setUserId(Long userId) {
        this.userId = userId;
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

    public XContentBuilder getBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", getId());
            builder.timeField("created_date", getCreatedDate());
            builder.field("person_id", getPersonId());
            builder.field("user_id", getUserId());
            builder.field("deal_id", getDealId());
            builder.field("type", getType());
        }
        builder.endObject();

        return builder;
    }
}


