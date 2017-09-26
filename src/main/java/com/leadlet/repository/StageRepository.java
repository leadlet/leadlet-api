package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
    Page<Stage> findByAppAccount(AppAccount appAccount, Pageable page);
    Stage findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    void deleteByIdAndAndAppAccount(Long id, AppAccount appAccount);

}
