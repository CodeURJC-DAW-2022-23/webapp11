package com.techmarket.app.service;


import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;


    public Page<Purchase> getByUserIdOrdered(Long userId, Pageable pageable) {
        return purchaseRepository.findByUserIdOrderByTimestampDesc(userId, pageable);
    }

    public Purchase getPurchaseById(Long purchaseId) {
        return purchaseRepository.findByPurchaseId(purchaseId);
    }


    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}

