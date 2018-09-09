package com.leadlet.repository;

import com.leadlet.domain.Activity;
import com.leadlet.domain.enumeration.SyncStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAppAccount_Id(Long appAccountId);

    Activity findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);

    Page<Activity> findByPerson_Id(Long id, Pageable page);

    Page<Activity> findByAgent_Id(Long id, Pageable page);

    Page<Activity> findByDeal_Id(Long id, Pageable page);

    Page<Activity> findAllBySyncStatusAndCreatedDateLessThan(SyncStatus syncStatus, Instant maxDate, Pageable page);

    @Modifying
    @Query("update #{#entityName} activity set activity.syncStatus = ?1 where activity.id in ?2")
    void updateActivitiesStatus(SyncStatus syncStatus, List<Long> ids);

    List<Activity> findAllByIdIn(List<Long> ids);

}
