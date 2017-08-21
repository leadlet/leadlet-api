package com.leadlet.repository;

import com.leadlet.domain.PipelineStage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PipelineStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PipelineStageRepository extends JpaRepository<PipelineStage,Long> {
    
}
