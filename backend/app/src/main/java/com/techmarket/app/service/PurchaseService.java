package com.techmarket.app.service;

import com.techmarket.app.model.Purchase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

     public List<Purchase> getUserPurchases(String userId) {
        // Get all purchases from database by user id
        return new ArrayList<Purchase>();
    }

    public Purchase getPurchase(String purchaseId) {
        // Get purchase from database by purchase id
        return new Purchase();
    }

    public void updatePurchase(Purchase purchase) {
        // Update purchase in database, useful for cancelling a purchase
    }

    public void addPurchase(Purchase purchase) {
        // Add purchase to database
    }

    public void deletePurchase(String purchaseId) {
        // Delete purchase from database
    }

    private void deleteAllPurchases() {
        // Delete all purchases from database
        // Useful for deleting all purchases when the database is reset
    }

    public void deleteAllPurchasesByUserId(String userId) {
        // Delete all purchases from database by user id
        // Useful for deleting all purchases of a user when the user is deleted
    }

    public void deleteAllPurchasesByProductId(String productId) {
        // Delete all purchases from database by product id
        // Useful for deleting all purchases of a product when the product is deleted
    }
}
