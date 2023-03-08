package com.techmarket.app.service;


import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
