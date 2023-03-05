package com.techmarket.app.Repositories;


import com.techmarket.app.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@EnableJpaRepositories
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    Page<Purchase> findByUserId(Long userId, org.springframework.data.domain.Pageable pageable);

    Page<Purchase> findAll(org.springframework.data.domain.Pageable pageable);

    // Get a purchase by its ID, used to generate an invoice
    Purchase findByPurchaseId(Long purchaseId);
    ArrayList<Purchase> findFirst10ByUserIdOrderByPurchaseIdDesc(Long id);
}