package com.leadlet.repository;

import com.leadlet.domain.Objective;
import com.leadlet.domain.User;
import com.leadlet.domain.enumeration.ActivityType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Objective entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjectiveRepository extends JpaRepository<Objective,Long> {
    Objective findOneByNameAndUserAndAppAccount_Id(ActivityType name, User user, Long appAccountId);
    List<Objective> findAllByUser_IdAndAppAccount_Id(Long userId, Long appAccountId);

}
