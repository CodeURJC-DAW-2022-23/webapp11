package com.techmarket.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.sql.Blob;

@Entity
@EnableAutoConfiguration
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private String productId;
    private String reviewId;
    private String userId; // This entity can be used for user profile pictures, reviews, and products, so we need to know which one it is, the rest of the fields will be null
    private String fileName;  // This is the image url, we will use this to send the image to the frontend

    @Lob
    @JsonIgnore  // We don't want to send the image blob to the frontend because it's too big, we will send the image url instead
    private Blob imageBlob;  // We don't use byte[] because it will end up taking too much space in the server's memory

    public Image(Long imageId, String productId, String reviewId, String userId, String fileName, Blob imageBlob) {
        this.imageId = imageId;
        this.productId = productId;
        this.reviewId = reviewId;
        this.userId = userId;
        this.fileName = fileName;
        this.imageBlob = imageBlob;
    }

    public Image() {

    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String image) {
        this.fileName = image;
    }

    public Blob getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(Blob imageBlob) {
        this.imageBlob = imageBlob;
    }
}
