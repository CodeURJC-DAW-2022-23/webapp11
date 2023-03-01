package com.techmarket.app.controller;

import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Image;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller

public class PurchasesController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    // Get the purchases of a user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/purchases")
    public String purchases(Model model) {
        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        // Get the purchases of the user
        List<Purchase> purchases = purchaseRepository.findByUserId(String.valueOf(user.getId()));  // Get the purchases of the user, the id is a long, so we need to convert it to a string for the query to work
        model.addAttribute("purchases", purchases);  // Mustache will loop through the list and display the purchases, or at least it should
        // Model the product ids to be able to leave a review
        List<String> productIds = new ArrayList<>();
        for (Purchase purchase : purchases) {
            productIds.add(String.valueOf(purchase.getProduct().getProductId()));
        }
        model.addAttribute("productId", productIds);  // Mustache will loop through the list, and we will have the product ids on the buttons according to the index
        // Model the product names and thumbnails to be able to display them
        List<String> productNames = new ArrayList<>();
        List<Image> productThumbnails = new ArrayList<>();
        for (Purchase purchase : purchases) {
            productNames.add(purchase.getProduct().getProductName());
            productThumbnails.add(purchase.getProduct().getMainImage());
        }
        model.addAttribute("productName", productNames);  // Mustache will loop through the list, and will display the product names according to the index
        model.addAttribute("productThumbnail", productThumbnails);
        return "purchases";
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
