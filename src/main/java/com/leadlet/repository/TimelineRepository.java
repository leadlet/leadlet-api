package com.leadlet.repository;

import com.leadlet.domain.Timeline;
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
 * Spring Data JPA repository for the Timeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findAllByIdIn(List<Long> ids);

    Page<Timeline> findAllBySyncStatusAndCreatedDateLessThan(SyncStatus syncStatus, Instant maxDate, Pageable page);

    @Modifying
    @Query("update #{#entityName} timeline set timeline.syncStatus = ?1 where deal.id in ?2")
    void updateTimelinesStatus(SyncStatus syncStatus, List<Long> ids);

}
