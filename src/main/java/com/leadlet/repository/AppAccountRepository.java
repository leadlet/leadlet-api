package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the AppAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppAccountRepository extends JpaRepository<AppAccount,Long> {
    Optional<AppAccount> findOneByName(String name);
}
