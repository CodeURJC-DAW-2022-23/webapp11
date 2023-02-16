package com.techmarket.app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String PurchaseId;
    @OneToMany
    private List<Product> products;
    private String timestamp;
    private String address;
    private String price;
    private String userId;

    public Purchase(String purchaseId, List<Product> products, String timestamp, String address, String price, String userId) {
        this.PurchaseId = purchaseId;
        this.products = products;
        this.timestamp = timestamp;
        this.address = address;
        this.price = price;
        this.userId = userId;
    }

    public Purchase() {
        
    }
}
