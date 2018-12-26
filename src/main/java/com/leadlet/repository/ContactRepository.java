package com.leadlet.repository;

import com.leadlet.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> ,  JpaSpecificationExecutor<Contact> {
    Page<Contact> findByAppAccount_Id(Long appAccountId, Pageable page);
    Contact findOneByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);
    void deleteByIdInAndAppAccount_Id(List<Long> idList, Long appAccountId);
}
