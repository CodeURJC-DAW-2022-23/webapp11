package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Entity
@EnableAutoConfiguration
public class Product {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String description;
    @OneToMany
    private List<Image> images;

    @OneToOne
    private Image mainImage;  // This is the main image of the product, used for the product card on reviews, history, cart, etc.
    private double productPrice;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;
    private String discount;
    private int productStock;
    private String productName; // They have to use the same name as Mustache
    private String productUrl;
    @OneToMany
    private List<Review> reviews;


    //To add products
    public Product(String productName, String description, double price, String discount, int productStock, List<String> tags){
        this.productName = productName;
        this.description = description;
        this.productPrice = price;
        this.discount = discount;
        this.productStock = productStock;
        this.tags = tags;
    }

    public Product(Long productId, String description, List<Image> images, Image mainImage, double productPrice, List<String> tags, String discount, int productStock, String productName, String productUrl, List<Review> reviews) {
        this.productId = productId;
        this.description = description;
        this.images = images;
        this.mainImage = mainImage;
        this.productPrice = productPrice;
        this.tags = tags;
        this.discount = discount;
        this.productStock = productStock;
        this.productName = productName;
        this.productUrl = productUrl;
        this.reviews = reviews;
    }

    protected Product(){}

    public Product(long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double price) {
        this.productPrice = price;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
