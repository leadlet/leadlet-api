package com.leadlet.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealDTO implements Serializable {

    private Long id;

    private String title;

    private Integer priority;

    private Long stageId;

    private Long pipelineId;

    private Long personId;

    private Long organizationId;

    private Long ownerId;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private ZonedDateTime possibleCloseDate;

    private DealValueDTO dealValue;

    private SourceDTO source;

    private ChannelDTO channel;

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

    public Long getPersonId() {
        return personId;
    }

    public DealDTO setPersonId(Long personId) {
        this.personId = personId;
        return this;
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

    public SourceDTO getSource() {
        return source;
    }

    public void setSource(SourceDTO source) {
        this.source = source;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
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
