package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Page<Team> findByAppAccount(AppAccount appAccount, Pageable page);
    Team findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    Team findOneByAppAccountAndRootIsTrue(AppAccount appAccount);
    void deleteByIdAndAndAppAccount(Long id, AppAccount appAccount);

}
