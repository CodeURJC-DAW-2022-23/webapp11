package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService ReviewService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByProduct(@PathVariable Long id){
        List<Review> reviewList = ReviewService.getAllReviewsByProductId(id);
        return ResponseEntity.ok(reviewList);

    }
}
