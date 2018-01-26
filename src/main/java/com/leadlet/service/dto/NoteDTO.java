package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class NoteDTO implements Serializable {

    private Long id;

    private String content;

    private Long personId;

    private Long organizationId;

    private Long dealId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteDTO)) return false;
        NoteDTO noteDTO = (NoteDTO) o;
        return Objects.equals(id, noteDTO.id) &&
            Objects.equals(content, noteDTO.content) &&
            Objects.equals(personId, noteDTO.personId) &&
            Objects.equals(organizationId, noteDTO.organizationId) &&
            Objects.equals(dealId, noteDTO.dealId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, content, personId, organizationId, dealId);
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", personId=" + personId +
            ", organizationId=" + organizationId +
            ", dealId=" + dealId +
            '}';
    }
}
