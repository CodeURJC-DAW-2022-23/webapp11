package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String purchaseId;
    // Instead of String ids, we use product and User to avoid having to query the database for the product and user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String timestamp;
    private String address;
    private String price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String paymentMethod;
    private boolean isCancelled;

    public Purchase(String purchaseId, Product product, String timestamp, String address, String price, User user, String paymentMethod, boolean isCancelled) {
        this.purchaseId = purchaseId;
        this.product = product;
        this.timestamp = timestamp;
        this.address = address;
        this.price = price;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.isCancelled = isCancelled;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
