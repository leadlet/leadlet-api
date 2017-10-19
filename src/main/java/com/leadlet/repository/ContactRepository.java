package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Contact;
import com.leadlet.domain.enumeration.ContactType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> ,  JpaSpecificationExecutor<Contact> {
    Page<Contact> findByAppAccount_Id(Long appAccountId, Pageable page);
    Page<Contact> findByTypeAndAppAccount_Id(ContactType type, Long appAccountId, Pageable page);

    Contact findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

    void deleteByIdAndAppAccount_Id(Long id, Long appAccountId);
}
