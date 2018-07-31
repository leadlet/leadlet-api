package com.leadlet.repository;

import com.leadlet.domain.DealChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ChannelRepository extends JpaRepository<DealChannel,Long>, JpaSpecificationExecutor<DealChannel> {

    Page<DealChannel> findByAppAccount_Id(Long appAccountId, Pageable page);
    DealChannel findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

}
