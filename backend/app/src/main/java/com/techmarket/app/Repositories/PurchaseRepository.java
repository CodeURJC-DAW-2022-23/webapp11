package com.techmarket.app.Repositories;


import com.techmarket.app.model.Purchase;



public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    //List<Purchase> findByPurchaseId(String purchaseId);
    //Not really sure if we need this one

    Purchase findByUserId(String userId);


}