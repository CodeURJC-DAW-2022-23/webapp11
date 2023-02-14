package com.techmarket.app.controller;

import com.techmarket.app.model.Purchase;
import com.techmarket.app.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class PurchasesController {
    @Autowired
    private PurchaseService purchaseService;

    public Purchase getPurchase(String purchaseId) {
        return purchaseService.getPurchase(purchaseId);
    }
}
