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

    // Generate an invoice for a purchase in PDF format
    @GetMapping("/invoice")
    public String invoice(Model model, @RequestParam("purchase") String purchaseId) {
        Purchase purchase = purchaseService.getPurchase(purchaseId);
        model.addAttribute("purchase", purchase);
        // Return the invoice.html template, which will be converted to PDF by the browser because of the content-type
        return "invoice";
    }

    // Cancel a purchase
    @GetMapping("/cancel-order")
    public String cancelOrder(Model model, @RequestParam("purchase") String purchaseId) {
        Purchase purchase = purchaseService.getPurchase(purchaseId);
        purchase.setCancelled(true);
        purchaseService.updatePurchase(purchase);
        return "redirect:/purchases?user=" + purchase.getUserId();  // Redirect to the purchases page
    }
}
