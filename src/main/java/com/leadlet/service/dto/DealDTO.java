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
public class DealDTO implements Serializable {

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

    public DealStatus getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(DealStatus dealStatus) {
        this.dealStatus = dealStatus;
    }

    private LostReasonDTO lostReason;

    public LostReasonDTO getLostReason() {
        return lostReason;
    }

    public void setLostReason(LostReasonDTO lostReason) {
        this.lostReason = lostReason;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public DealDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DealDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public DealDTO setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public StageDTO getStage() {
        return stage;
    }

    public DealDTO setStage(StageDTO stage) {
        this.stage = stage;
        return this;
    }

    public PipelineDTO getPipeline() {
        return pipeline;
    }

    public DealDTO setPipeline(PipelineDTO pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public DealDTO setContact(ContactDTO contact) {
        this.contact = contact;
        return this;
    }

    public UserDTO getAgent() {
        return agent;
    }

    public DealDTO setAgent(UserDTO agent) {
        this.agent = agent;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DealDTO setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public DealDTO setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public ZonedDateTime getPossibleCloseDate() {
        return possibleCloseDate;
    }

    public DealDTO setPossibleCloseDate(ZonedDateTime possibleCloseDate) {
        this.possibleCloseDate = possibleCloseDate;
        return this;
    }

    public DealValueDTO getDealValue() {
        return dealValue;
    }

    public DealDTO setDealValue(DealValueDTO dealValue) {
        this.dealValue = dealValue;
        return this;
    }

    public SourceDTO getDealSource() {
        return dealSource;
    }

    public DealDTO setDealSource(SourceDTO dealSource) {
        this.dealSource = dealSource;
        return this;
    }

    public ChannelDTO getDealChannel() {
        return dealChannel;
    }

    public DealDTO setDealChannel(ChannelDTO dealChannel) {
        this.dealChannel = dealChannel;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealDTO)) return false;
        DealDTO dealDTO = (DealDTO) o;
        return Objects.equals(id, dealDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
