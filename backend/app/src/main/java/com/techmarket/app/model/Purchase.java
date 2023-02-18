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
    private boolean isCancelled;

    public Purchase(String purchaseId, List<Product> products, String timestamp, String address, String price, String userId) {
        this.PurchaseId = purchaseId;
        this.products = products;
        this.timestamp = timestamp;
        this.address = address;
        this.price = price;
        this.userId = userId;
        this.isCancelled = false;
    }

    public Purchase() {
        
    }

    public String getPurchaseId() {
        return PurchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        PurchaseId = purchaseId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
