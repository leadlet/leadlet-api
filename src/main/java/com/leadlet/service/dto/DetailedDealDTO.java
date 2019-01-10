package com.leadlet.service.dto;


import com.leadlet.domain.enumeration.ActivityStatus;
import com.leadlet.domain.enumeration.DealStatus;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Deal entity.
 */
public class DetailedDealDTO implements Serializable {

    private Long id;

    private String title;

    private Integer priority;

    private StageDTO stage;

    private PipelineDTO pipeline;

    private ContactDTO contact;

    private UserDTO agent;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private ZonedDateTime possibleCloseDate;

    private DealValueDTO dealValue;

    private SourceDTO dealSource;

    private ChannelDTO dealChannel;

    private Set<ProductDTO> products;

    private ActivityStatus activityStatus;

    private DealStatus dealStatus;

    public DetailedDealDTO() {
    }

    public Long getId() {
        return id;
    }

    public DetailedDealDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DetailedDealDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public DetailedDealDTO setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public StageDTO getStage() {
        return stage;
    }

    public DetailedDealDTO setStage(StageDTO stage) {
        this.stage = stage;
        return this;
    }

    public PipelineDTO getPipeline() {
        return pipeline;
    }

    public DetailedDealDTO setPipeline(PipelineDTO pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public DetailedDealDTO setContact(ContactDTO contact) {
        this.contact = contact;
        return this;
    }

    public UserDTO getAgent() {
        return agent;
    }

    public DetailedDealDTO setAgent(UserDTO agent) {
        this.agent = agent;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DetailedDealDTO setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public DetailedDealDTO setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public ZonedDateTime getPossibleCloseDate() {
        return possibleCloseDate;
    }

    public DetailedDealDTO setPossibleCloseDate(ZonedDateTime possibleCloseDate) {
        this.possibleCloseDate = possibleCloseDate;
        return this;
    }

    public DealValueDTO getDealValue() {
        return dealValue;
    }

    public DetailedDealDTO setDealValue(DealValueDTO dealValue) {
        this.dealValue = dealValue;
        return this;
    }

    public SourceDTO getDealSource() {
        return dealSource;
    }

    public DetailedDealDTO setDealSource(SourceDTO dealSource) {
        this.dealSource = dealSource;
        return this;
    }

    public ChannelDTO getDealChannel() {
        return dealChannel;
    }

    public DetailedDealDTO setDealChannel(ChannelDTO dealChannel) {
        this.dealChannel = dealChannel;
        return this;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public DetailedDealDTO setProducts(Set<ProductDTO> products) {
        this.products = products;
        return this;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public DetailedDealDTO setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
        return this;
    }

    public DealStatus getDealStatus() {
        return dealStatus;
    }

    public DetailedDealDTO setDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailedDealDTO)) return false;
        DetailedDealDTO that = (DetailedDealDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
