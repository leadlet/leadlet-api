package com.leadlet.repository;

import com.leadlet.domain.Activity;
import com.leadlet.domain.AppAccount;
import com.leadlet.domain.enumeration.ActivityType;
import com.leadlet.service.dto.ActivityCompleted;
import javafx.util.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.*;


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

    Page<Activity> findByOrganization_Id(Long id, Pageable page);

    Page<Activity> findByAgent_Id(Long id, Pageable page);

    Page<Activity> findByDeal_Id(Long id, Pageable page);

    //  AND WEEK(activity.closed_date) = WEEK(CURDATE());
    @Query("SELECT new javafx.util.Pair( activity.type, COUNT(activity)) FROM Activity activity " +
        "LEFT JOIN User user ON activity.agent.id = user.id WHERE activity.done=true " +
        "AND ( activity.closedDate BETWEEN ?2 AND ?3) " +
        "AND user.team.id = ?1 GROUP BY activity.type")
    List<Pair<ActivityType,Long>> calculateCompletedActivitiesTeamBetweenDates(long teamId, Date minDate, Date maxDate);

}
