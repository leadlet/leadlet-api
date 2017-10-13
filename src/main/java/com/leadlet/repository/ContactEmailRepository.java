package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.ContactEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail,Long> {

    Page<ContactEmail> findByAppAccount(AppAccount appAccount, Pageable page);
    ContactEmail findOneByIdAndAppAccount(Long id, AppAccount appAccount);
    void deleteByIdAndAndAppAccount(Long id, AppAccount appAccount);

}
