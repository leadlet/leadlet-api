package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Pipeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.nio.channels.Pipe;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Pipeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PipelineRepository extends JpaRepository<Pipeline,Long> {
    Page<Pipeline> findByAppAccount(AppAccount appAccount, Pageable page);
}
