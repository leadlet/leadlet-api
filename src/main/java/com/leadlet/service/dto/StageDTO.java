package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Stage entity.
 */
public class StageDTO implements Serializable {

    private Long id;

    private String name;

    private Long pipelineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StageDTO stageDTO = (StageDTO) o;
        if(stageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pipelineId='" + getPipelineId() + "'" +
            "}";
    }
}
