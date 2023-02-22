package com.techmarket.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ReviewController {
    // A user can only review a product once, and only if they have purchased it
    @GetMapping("/review")
    public String review() {
        return "review";
    }

    // We have to decide how to handle the review submission
    @GetMapping("/review/{productId}")
    public String reviewProduct() {
        return "review";
    }

}
