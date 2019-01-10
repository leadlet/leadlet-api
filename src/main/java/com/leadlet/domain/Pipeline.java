package com.leadlet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pipeline.
 */
@Entity
@Table(name = "pipeline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pipeline extends AbstractAccountSpecificEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(mappedBy = "pipeline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stage> stages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pipeline name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Stage> getStages() {
        return stages;
    }

    public Pipeline addStage(Stage stage) {
        this.stages.add(stage);
        stage.setPipeline(this);
        return this;
    }

    public Pipeline removeStage(Stage stage) {
        this.stages.remove(stage);
        stage.setPipeline(null);
        return this;
    }

    public void setPipelineStages(Set<Stage> pipelineStages) {
        this.stages = pipelineStages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pipeline pipeline = (Pipeline) o;
        if (pipeline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pipeline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pipeline{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
