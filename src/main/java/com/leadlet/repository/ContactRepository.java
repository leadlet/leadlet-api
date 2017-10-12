package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    Page<Contact> findByAppAccount(AppAccount appAccount, Pageable page);
    Contact findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    void deleteByIdAndAppAccount(Long id, AppAccount appAccount);
}
