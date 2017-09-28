package com.leadlet.repository;

import com.leadlet.domain.Activity;
import com.leadlet.domain.AppAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Page<Activity> findByAppAccount(AppAccount appAccount, Pageable page);
    Activity findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    void deleteByIdAndAppAccount(Long id, AppAccount appAccount);
}
