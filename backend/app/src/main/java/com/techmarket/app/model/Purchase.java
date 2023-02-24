package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String purchaseId;
    @OneToOne
    private Product product;  // We will create a new "Purchase" object for each product in the checkout page, easier to handle
    private String timestamp;
    private String address;
    private String price;
    private String userId;
    private String paymentMethod;
    private boolean isCancelled;

    public Purchase(String purchaseId, Product product, String timestamp, String address, String price, String userId, String paymentMethod) {
        this.purchaseId = purchaseId;
        this.product = product;
        this.timestamp = timestamp;
        this.address = address;
        this.price = price;
        this.userId = userId;
        this.isCancelled = false;
        this.paymentMethod = paymentMethod;
    }

    public Purchase() {
        
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product products) {
        this.product = product;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
