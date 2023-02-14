package com.techmarket.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String purchaseId;
    private List<Product> products;
    private String timestamp;
    private String address;
    private String price;
    private String userId;

    public Purchase(String purchaseId, List<Product> products, String timestamp, String address, String price, String userId) {
        this.purchaseId = purchaseId;
        this.products = products;
        this.timestamp = timestamp;
        this.address = address;
        this.price = price;
        this.userId = userId;
    }
}
