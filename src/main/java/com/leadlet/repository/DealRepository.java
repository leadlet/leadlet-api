package com.leadlet.repository;

import com.leadlet.domain.Deal;
import com.leadlet.domain.enumeration.SyncStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the Deal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealRepository extends JpaRepository<Deal, Long>, JpaSpecificationExecutor<Deal> {
    Page<Deal> findByAppAccount_IdOrderByIdAsc(Long appAccountId, Pageable page);

    Deal findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);

    Page<Deal> findAllByAppAccount_IdAndStage_IdOrderByPriorityAsc(Long appAccountId, Long stageId, Pageable page);

    Page<Deal> findAllByAppAccount_IdAndContact_IdOrderByPriorityAsc(Long appAccountId, Long contactId, Pageable page);

    Page<Deal> findAllByAppAccount_IdAndDealSource_Id(Long appAccountId, Long sourceId, Pageable page);

    @Query("select sum(deal.dealValue.potentialValue) from #{#entityName} deal where deal.appAccount.id = ?1 and deal.stage.id = ?2")
    Double calculateDealTotalByStageId(Long id, Long stageId);

    List<Deal> findAllByIdIn(List<Long> ids);

    Page<Deal> findAllBySyncStatusAndCreatedDateLessThan(SyncStatus syncStatus, Instant maxDate, Pageable page);

    @Modifying
    @Query("update #{#entityName} deal set deal.syncStatus = ?1 where deal.id in ?2")
    void updateDealsStatus(SyncStatus syncStatus, List<Long> ids);

}
