package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Deal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealRepository extends JpaRepository<Deal,Long> {
    Page<Deal> findByAppAccount(AppAccount appAccount, Pageable page);
    Deal findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    void deleteByIdAndAndAppAccount(Long id, AppAccount appAccount);

}
