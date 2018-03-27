package com.leadlet.repository;

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
public interface DealRepository extends JpaRepository<Deal,Long>,  JpaSpecificationExecutor<Deal> {
    Page<Deal> findByAppAccount_IdOrderByIdAsc(Long appAccountId, Pageable page);

    Deal findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);

    @Modifying
    @Query("update #{#entityName} deal set deal.priority = (deal.priority + 1) where deal.appAccount.id = ?1 and deal.stage.id = ?2 and deal.priority >= ?3")
    void shiftDealsUp(Long appAccountId, Long stageId, Integer priority);

    Page<Deal> findAllByAppAccount_IdAndStage_IdOrderByPriorityAsc(Long appAccountId, Long stageId, Pageable page);

    Page<Deal> findAllByAppAccount_IdAndPerson_IdOrderByPriorityAsc(Long appAccountId, Long personId, Pageable page);

    @Query("select sum(deal.dealValue.potentialValue) from #{#entityName} deal where deal.appAccount.id = ?1 and deal.stage.id = ?2")
    Double calculateDealTotalByStageId(Long id, Long stageId);

}
