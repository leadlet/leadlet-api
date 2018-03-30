package com.leadlet.service.mapper;

import com.leadlet.domain.User;
import com.leadlet.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class, TeamMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source = "team.id", target = "teamId")
    UserDTO toDto(User user);

    @Mapping(source = "teamId", target = "team")
    User toEntity(UserDTO userDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
