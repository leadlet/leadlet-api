package com.leadlet.repository;

import com.leadlet.domain.Team;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    Optional<Team> findOneByName(String name);

}
