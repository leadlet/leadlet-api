package com.leadlet.repository;

import com.leadlet.domain.ContactEmail;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail,Long> {
    
}
