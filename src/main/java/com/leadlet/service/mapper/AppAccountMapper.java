package com.leadlet.service.mapper;

import com.leadlet.domain.*;
import com.leadlet.service.dto.AppAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AppAccount and its DTO AppAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppAccountMapper extends EntityMapper <AppAccountDTO, AppAccount> {

    @Mapping(target = "subscriptionPlans", ignore = true)
    @Mapping(target = "users", ignore = true)
    AppAccount toEntity(AppAccountDTO appAccountDTO);
    default AppAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppAccount appAccount = new AppAccount();
        appAccount.setId(id);
        return appAccount;
    }
}
