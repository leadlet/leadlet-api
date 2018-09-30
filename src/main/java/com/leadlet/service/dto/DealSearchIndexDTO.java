package com.leadlet.service.dto;


import com.leadlet.domain.Deal;
import com.leadlet.domain.Product;
import com.leadlet.domain.enumeration.DealStatus;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the Deal entity.
 */
public class DealSearchIndexDTO implements Serializable {

    private Long id;
    private Date createdDate;
    private Long pipelineId;
    private Long stageId;
    private Long appAccountId;
    private Integer priority;
    private String source;
    private String channel;
    private String[] products;
    private DealStatus dealStatus;
    private String lostReason;

    public DealSearchIndexDTO(){

    }

    public DealSearchIndexDTO(Deal deal){

        this.id = deal.getId();
        this.createdDate = new Date(deal.getCreatedDate().toEpochMilli());
        this.pipelineId = deal.getPipeline().getId();
        this.stageId = deal.getStage().getId();
        this.priority = deal.getPriority();
        this.source = !StringUtils.isEmpty(deal.getDealSource()) ? deal.getDealSource().getName() : "";
        this.channel = !StringUtils.isEmpty(deal.getDealChannel()) ? deal.getDealChannel().getName() : "";
        this.dealStatus = deal.getDealStatus();
        this.lostReason = deal.getLostReason().getName();
        this.appAccountId = deal.getAppAccount().getId();

        if( deal.getProducts() != null ){
            this.products = deal.getProducts().stream().map(Product::getDescription).toArray(size -> new String[size]);

        }
    }

    public Long getId() {
        return id;
    }

    public DealSearchIndexDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public DealSearchIndexDTO setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public DealSearchIndexDTO setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public Long getStageId() {
        return stageId;
    }

    public DealSearchIndexDTO setStageId(Long stageId) {
        this.stageId = stageId;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public DealSearchIndexDTO setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getSource() {
        return source;
    }

    public DealSearchIndexDTO setSource(String source) {
        this.source = source;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public DealSearchIndexDTO setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String[] getProducts() {
        return products;
    }

    public DealSearchIndexDTO setProducts(String[] products) {
        this.products = products;
        return this;
    }

    public DealStatus getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getLostReason() {
        return lostReason;
    }

    public void setLostReason(String lostReason) {
        this.lostReason = lostReason;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public DealSearchIndexDTO setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
        return this;
    }

    public XContentBuilder getBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", getId());
            builder.timeField("created_date", getCreatedDate());
            builder.field("pipeline_id", getPipelineId());
            builder.field("stage_id", getStageId());
            builder.field("priority", getPriority());
            builder.field("source", getSource());
            builder.field("channel", getChannel());
            builder.field("products", getProducts());
            builder.field("app_account_id", getAppAccountId());
            builder.field("dealStatus", getDealStatus());
            builder.field("lostReason", getLostReason());
        }
        builder.endObject();

        return builder;
    }
}


