package com.leadlet.service.mapper;

import com.leadlet.domain.User;
import com.leadlet.service.dto.UserLoginDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Deal and its DTO DetailedDealDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class, AppAccountMapper.class})
public interface UserLoginMapper extends EntityMapper<UserLoginDTO, User> {

    UserLoginDTO toDto(User user);

    User toEntity(UserLoginDTO userLoginDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
