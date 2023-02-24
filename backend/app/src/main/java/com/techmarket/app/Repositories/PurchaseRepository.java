package com.techmarket.app.Repositories;


import com.techmarket.app.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    List<Purchase> findByUserId(String userId);

    // Get a purchase by its ID, used to generate an invoice
    Purchase findByPurchaseId(String purchaseId);


}