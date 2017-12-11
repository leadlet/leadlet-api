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
    Page<Deal> findByAppAccount_Id(Long appAccountId, Pageable page);
    Deal findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);

    @Modifying
    @Query("update #{#entityName} deal set deal.order = deal.order + 1 where deal.appAccount.id = ?1 and deal.stage.id = ?2 and deal.order >= ?3")
    void shiftDealsUp(Long id, Long stageId, Integer order);

    @Modifying
    @Query("update #{#entityName} deal set deal.stage.id = ?2, deal.order=?3 where deal.id = ?1")
    void setStageAndOrder(Long id, Long stageId, Integer order);


}
