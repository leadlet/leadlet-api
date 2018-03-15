package com.leadlet.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TeamDTO implements Serializable {

    private Long id;

    private String name;

    private UserDTO teamLeader;

    private List<UserDTO> members;

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

    public UserDTO getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(UserDTO teamLeader) {
        this.teamLeader = teamLeader;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamDTO)) return false;
        TeamDTO teamDTO = (TeamDTO) o;
        return Objects.equals(id, teamDTO.id) &&
            Objects.equals(name, teamDTO.name) &&
            Objects.equals(teamLeader, teamDTO.teamLeader) &&
            Objects.equals(members, teamDTO.members);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, teamLeader, members);
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", teamLeader=" + teamLeader +
            ", members=" + members +
            '}';
    }
}
