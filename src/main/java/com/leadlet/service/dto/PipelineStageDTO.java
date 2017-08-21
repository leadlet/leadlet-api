package com.leadlet.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PipelineStage entity.
 */
public class PipelineStageDTO implements Serializable {

    private Long id;

    private Integer stageOrder;

    private Long pipelineId;

    private Long stageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public Long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(Long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PipelineStageDTO pipelineStageDTO = (PipelineStageDTO) o;
        if(pipelineStageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pipelineStageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PipelineStageDTO{" +
            "id=" + getId() +
            ", stageOrder='" + getStageOrder() + "'" +
            "}";
    }
}
