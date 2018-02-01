package com.leadlet.service.dto;

import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Deal entity.
 */
public class DealDetailDTO extends  DealDTO {

    private static final long serialVersionUID = 7878276242200417567L;

    private StageDTO stage;

    private PersonDTO person;

    private OrganizationDTO organization;

    private UserDTO owner;

    private Instant createdDate;

    private Instant lastModifiedDate;

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

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public DealDetailDTO setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public DealDetailDTO setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public StageDTO getStage() {
        return stage;
    }

    public DealDetailDTO setStage(StageDTO stage) {
        this.stage = stage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealDetailDTO)) return false;
        if (!super.equals(o)) return false;
        DealDetailDTO that = (DealDetailDTO) o;
        return Objects.equals(person, that.person) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), person, organization, owner, createdDate, lastModifiedDate);
    }
}
