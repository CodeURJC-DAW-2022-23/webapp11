package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PurchasesController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    // Get the purchases of a user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/purchases")
    public String purchases(Model model, @PageableDefault(size = 10) Pageable pageable) {
        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        // Get the purchases of the user
        Page<Purchase> purchases = purchaseRepository.findByUserId(user.getId(), pageable);
        // Check if the user has any purchases
        if (!purchases.isEmpty()) {
            model.addAttribute("purchases", purchases.getContent());
            model.addAttribute("total", purchases.getTotalElements());
            if (purchases.getTotalElements() > 10) {
                model.addAttribute("hasMore", true);
            } else {
                model.addAttribute("hasMore", false);
            }
        } else {
            model.addAttribute("purchases", null);
            model.addAttribute("total", 0);
            model.addAttribute("hasMore", false);
        }
        return "purchases";
    }

    @GetMapping("/purchases/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {

        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Purchase> page = purchaseRepository.findByUserId(user.getId(), pageable);


        return JSONService.getPurchaseStringResponseEntity(page);
    }

    // Generate an invoice for a purchase in PDF format
    @GetMapping("/invoice/{PurchaseId}")
    public String invoice(Model model, @RequestParam("purchaseId") String purchaseId) {  // Get the purchase id from the URL
        // Get the purchase
        Purchase purchase = purchaseRepository.findByPurchaseId(purchaseId);
        model.addAttribute("purchase", purchase);  // Mustache will display the purchase details
        return "invoice";  // Return the invoice template, it will be rendered as a PDF file by the browser because of the content type
    }

    // Cancel a purchase
    @GetMapping("/return/{PurchaseId}")
    public String cancelOrder(Model model, @RequestParam("purchaseId") String purchaseId) {  // Get the purchase id from the URL
        // Get the purchase
        Purchase purchase = purchaseRepository.findByPurchaseId(purchaseId);
        // Delete the purchase from the database
        purchaseRepository.delete(purchase);
        // Show a message to the user
        model.addAttribute("message", "Your purchase has been canceled");
        return "redirect:/purchases";
    }
}
