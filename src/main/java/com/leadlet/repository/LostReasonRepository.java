package com.leadlet.repository;

import com.leadlet.domain.LostReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LostReasonRepository extends JpaRepository<LostReason, Long>, JpaSpecificationExecutor<LostReason> {

    Page<LostReason> findByAppAccount_Id(Long appAccountId, Pageable page);
    LostReason findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
}
