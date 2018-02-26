package com.leadlet.repository;

import com.leadlet.domain.Activity;
import com.leadlet.domain.AppAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByAppAccount_Id(Long appAccountId);
    Activity findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);
    Page<Activity> findByPerson_Id(Long id, Pageable page);
    Page<Activity> findByOrganization_Id(Long id, Pageable page);
    Page<Activity> findByUser_Id(Long id, Pageable page);
}
