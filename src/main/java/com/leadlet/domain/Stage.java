package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Stage.
 */
@Entity
@Table(name = "stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "stage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PipelineStage> pipelineStages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Stage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PipelineStage> getPipelineStages() {
        return pipelineStages;
    }

    public Stage pipelineStages(Set<PipelineStage> pipelineStages) {
        this.pipelineStages = pipelineStages;
        return this;
    }

    public Stage addPipelineStage(PipelineStage pipelineStage) {
        this.pipelineStages.add(pipelineStage);
        pipelineStage.setStage(this);
        return this;
    }

    public Stage removePipelineStage(PipelineStage pipelineStage) {
        this.pipelineStages.remove(pipelineStage);
        pipelineStage.setStage(null);
        return this;
    }

    public void setPipelineStages(Set<PipelineStage> pipelineStages) {
        this.pipelineStages = pipelineStages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stage stage = (Stage) o;
        if (stage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
