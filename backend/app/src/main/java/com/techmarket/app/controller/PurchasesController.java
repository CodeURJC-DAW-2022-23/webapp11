package com.techmarket.app.controller;

import com.techmarket.app.model.Purchase;
import com.techmarket.app.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class PurchasesController {

    @Autowired
    private PurchaseService purchaseService;

    // Get the purchases of a user
    @GetMapping("/purchases")
    public String purchases(Model model, @RequestParam("user") String userId) {
        List<Purchase> purchases = purchaseService.getUserPurchases(userId);
        // We still have to do the 10 results per page thing, this will print all purchases
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
}
