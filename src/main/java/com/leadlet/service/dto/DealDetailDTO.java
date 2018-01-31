package com.leadlet.service.dto;


import com.leadlet.domain.Organization;
import com.leadlet.domain.Pipeline;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealDetailDTO implements Serializable {

    private Long id;

    private String title;

    private Integer priority;

    private StageDTO stage;

    private PipelineDTO pipeline;

    private PersonDTO person;

    private OrganizationDTO organization;

    private UserDTO owner;

    private ZonedDateTime possibleCloseDate;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private DealValueDTO dealValue;


    public Long getId() {
        return id;
    }

    public DealDetailDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DealDetailDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public DealDetailDTO setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public DealValueDTO getDealValue() {
        return dealValue;
    }

    public DealDetailDTO setDealValue(DealValueDTO dealValue) {
        this.dealValue = dealValue;
        return this;
    }

    public StageDTO getStage() {
        return stage;
    }

    public DealDetailDTO setStage(StageDTO stage) {
        this.stage = stage;
        return this;
    }

    public PipelineDTO getPipeline() {
        return pipeline;
    }

    public DealDetailDTO setPipeline(PipelineDTO pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public DealDetailDTO setPerson(PersonDTO person) {
        this.person = person;
        return this;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public DealDetailDTO setOrganization(OrganizationDTO organization) {
        this.organization = organization;
        return this;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public DealDetailDTO setOwner(UserDTO owner) {
        this.owner = owner;
        return this;
    }

    public ZonedDateTime getPossibleCloseDate() {
        return possibleCloseDate;
    }

    public DealDetailDTO setPossibleCloseDate(ZonedDateTime possibleCloseDate) {
        this.possibleCloseDate = possibleCloseDate;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DealDetailDTO setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public DealDetailDTO setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealDetailDTO)) return false;
        DealDetailDTO that = (DealDetailDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(stage, that.stage) &&
            Objects.equals(pipeline, that.pipeline) &&
            Objects.equals(person, that.person) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(possibleCloseDate, that.possibleCloseDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(dealValue, that.dealValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, priority, stage, pipeline, person, organization, owner, possibleCloseDate, createdDate, lastModifiedDate, dealValue);
    }

    @Override
    public String toString() {
        return "DealDetailDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", priority=" + priority +
            ", stage=" + stage +
            ", pipeline=" + pipeline +
            ", person=" + person +
            ", organization=" + organization +
            ", owner=" + owner +
            ", possibleCloseDate=" + possibleCloseDate +
            ", createdDate=" + createdDate +
            ", lastModifiedDate=" + lastModifiedDate +
            ", dealValue=" + dealValue +
            '}';
    }
}
