package com.techmarket.app.model;

public class Product {
    private String productName; // They have to use the same name as Mustache
    private String description;
    private String imgUrl;
    private String price;
    private String tags;
    private String brand;
    private int productStock;

    private String productId;
    private String ProductUrl;

    public Product(String productName, String description, String imgUrl, String price, String tags, String brand, int productStock, String productId, String productUrl) {
        this.productName = productName;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.tags = tags;
        this.brand = brand;
        this.productStock = productStock;
        this.productId = productId;
        ProductUrl = productUrl;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductUrl() {
        return ProductUrl;
    }

    public void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }
}
