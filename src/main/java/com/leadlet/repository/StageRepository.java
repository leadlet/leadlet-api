package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
    List<Stage> findAllByAppAccount_IdAndPipeline_Id(Long appAccountId, Long pipelineId);

    Page<Stage> findByAppAccount_Id(Long appAccountId, Pageable page);
    Stage findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByAppAccount_IdAndPipeline_Id(Long appAccountId, Long pipelineId);

}
