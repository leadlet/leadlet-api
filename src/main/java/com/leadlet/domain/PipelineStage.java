package com.leadlet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PipelineStage.
 */
@Entity
@Table(name = "pipeline_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PipelineStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stage_order")
    private Integer stageOrder;

    @ManyToOne
    private Pipeline pipeline;

    @ManyToOne
    private Stage stage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public PipelineStage stageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
        return this;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public PipelineStage pipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Stage getStage() {
        return stage;
    }

    public PipelineStage stage(Stage stage) {
        this.stage = stage;
        return this;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PipelineStage pipelineStage = (PipelineStage) o;
        if (pipelineStage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pipelineStage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PipelineStage{" +
            "id=" + getId() +
            ", stageOrder='" + getStageOrder() + "'" +
            "}";
    }
}
