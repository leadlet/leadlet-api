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

    private Long stageId;

    private Long pipelineId;

    private PersonDTO person;

    private Long organizationId;

    private Long ownerId;

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

    public Long getStageId() {
        return stageId;
    }

    public DealDTO setStageId(Long stageId) {
        this.stageId = stageId;
        return this;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public DealDTO setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
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

    public DealValueDTO getDealValue() {
        return dealValue;
    }

    public DealDTO setDealValue(DealValueDTO dealValue) {
        this.dealValue = dealValue;
        return this;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public DealDTO setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public DealDTO setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public ZonedDateTime getPossibleCloseDate() {
        return possibleCloseDate;
    }

    public DealDTO setPossibleCloseDate(ZonedDateTime possibleCloseDate) {
        this.possibleCloseDate = possibleCloseDate;
        return this;
    }

    public SourceDTO getDealSource() {
        return dealSource;
    }

    public void setDealSource(SourceDTO dealSource) {
        this.dealSource = dealSource;
    }

    public ChannelDTO getDealChannel() {
        return dealChannel;
    }

    public void setDealChannel(ChannelDTO dealChannel) {
        this.dealChannel = dealChannel;
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
