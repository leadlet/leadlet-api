package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class NoteDTO implements Serializable {

    private Long id;

    private String content;

    private Long contactId;

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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
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
        return Objects.equals(id, noteDTO.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", contactId=" + contactId +
            ", dealId=" + dealId +
            '}';
    }
}
