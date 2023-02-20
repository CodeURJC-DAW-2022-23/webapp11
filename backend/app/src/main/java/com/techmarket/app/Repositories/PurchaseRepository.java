package com.techmarket.app.Repositories;


import com.techmarket.app.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    //List<Purchase> findByPurchaseId(String purchaseId);
    //Not really sure if we need this one

    Purchase findByUserId(String userId);


}