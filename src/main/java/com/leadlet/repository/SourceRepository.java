package com.leadlet.repository;

import com.leadlet.domain.DealSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SourceRepository extends JpaRepository<DealSource,Long>, JpaSpecificationExecutor<DealSource> {

    Page<DealSource> findByAppAccount_Id(Long appAccountId, Pageable page);
    DealSource findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

}
