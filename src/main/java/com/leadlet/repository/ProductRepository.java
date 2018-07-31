package com.leadlet.repository;

import com.leadlet.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findByAppAccount_Id(Long appAccountId, Pageable page);
    Product findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

}
