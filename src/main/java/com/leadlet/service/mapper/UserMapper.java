package com.leadlet.service.mapper;

import com.leadlet.domain.Deal;
import com.leadlet.domain.User;
import com.leadlet.service.dto.DealDTO;
import com.leadlet.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User user);

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
