package com.leadlet.repository;

import com.leadlet.domain.EmailTemplates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmailTemplates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplates,Long> {
    
}
