package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class NoteDTO implements Serializable {

    private Long id;

    private String content;

    private Long personId;

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
            ", personId=" + personId +
            ", dealId=" + dealId +
            '}';
    }
}
