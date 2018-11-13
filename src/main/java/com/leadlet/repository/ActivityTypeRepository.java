package com.leadlet.repository;

import com.leadlet.domain.ActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType,Long>, JpaSpecificationExecutor<ActivityType> {

    Page<ActivityType> findByAppAccount_Id(Long appAccountId, Pageable page);
    ActivityType findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

}
