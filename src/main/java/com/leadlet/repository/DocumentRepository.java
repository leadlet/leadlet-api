package com.leadlet.repository;

import com.leadlet.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Document entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

    List<Document> findAllByAppAccount_Id(Long appAccountId);

    List<Document> findByPerson_IdAndAppAccount_Id(Long personId, Long appAccountId);

    List<Document> findByOrganization_IdAndAppAccount_Id(Long organizationId, Long appAccountId);

    List<Document> findByDeal_IdAndAppAccount_Id(Long dealId, Long appAccountId);
}
