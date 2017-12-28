package com.leadlet.repository;

import com.leadlet.domain.AppAccount;
import com.leadlet.domain.ContactPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactPhoneRepository extends JpaRepository<ContactPhone,Long> {

    ContactPhone findOneById(Long id);
    void deleteById(Long id);
}
