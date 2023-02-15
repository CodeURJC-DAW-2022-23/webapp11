package com.techmarket.app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String productId;

    private String description;
    private String imgUrl; // There should be multiple images
    private String price;
    private String tags;
    private String discount;
    private int productStock;
    private String productName; // They have to use the same name as Mustache
    private String productUrl;

    @OneToMany
    private List<Review> reviews;

    public Product(String productName, String description, String imgUrl, String price, String tags, String discount, int productStock, String productId, String productUrl, List<Review> reviews) {
        this.productName = productName;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.tags = tags;
        this.discount = discount;
        this.productStock = productStock;
        this.productId = productId;
        this.productUrl = productUrl;
        this.reviews = reviews;
    }

    public Product() {

    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String brand) {
        this.discount = brand;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductId() {
        return productId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
