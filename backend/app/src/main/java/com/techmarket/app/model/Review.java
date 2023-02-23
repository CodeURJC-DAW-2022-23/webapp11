package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.sql.Blob;
import java.util.List;

@Entity
@EnableAutoConfiguration
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String reviewId;
    private String productId;
    private String userId;
    private String reviewTitle;
    private int rating;
    private String reviewText;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Image> images;

    public Review(String reviewId, String productId, String userId, String reviewTitle, int rating, String reviewText, List<Image> images) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.reviewTitle = reviewTitle;
        this.rating = rating;
        this.reviewText = reviewText;
        this.images = images;
    }

    public Review() {

    }

    public Review(String reviewTitle, String reviewText, int rating) {
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
