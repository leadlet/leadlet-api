package com.leadlet.repository;

import com.leadlet.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByAppAccount_Id(Long appAccountId, Pageable page);
    Team findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
}
