package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TeamDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private List<UserDTO> members;

    private List<TeamObjectiveDTO> completedObjectives;
    private List<TeamObjectiveDTO> objectives;

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

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TeamObjectiveDTO> getCompletedObjectives() {
        return completedObjectives;
    }

    public TeamDTO setCompletedObjectives(List<TeamObjectiveDTO> completedObjectives) {
        this.completedObjectives = completedObjectives;
        return this;
    }

    public List<TeamObjectiveDTO> getObjectives() {
        return objectives;
    }

    public TeamDTO setObjectives(List<TeamObjectiveDTO> objectives) {
        this.objectives = objectives;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamDTO)) return false;
        TeamDTO teamDTO = (TeamDTO) o;
        return Objects.equals(id, teamDTO.id) &&
            Objects.equals(name, teamDTO.name) &&
            Objects.equals(description, teamDTO.description) &&
            Objects.equals(members, teamDTO.members);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, members);
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", members=" + members +
            '}';
    }
}
