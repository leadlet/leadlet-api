package com.leadlet.repository;

import com.leadlet.domain.OrganizationPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the OrganizationPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationPhoneRepository extends JpaRepository<OrganizationPhone, Long> {

    OrganizationPhone findOneById(Long id);
    void deleteById(Long id);
}
