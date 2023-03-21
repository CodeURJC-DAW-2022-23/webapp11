package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService ReviewService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long id){
        List<Review> reviewList = ReviewService.getAllReviewsByProductId(id);
        return ResponseEntity.ok(reviewList);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        ReviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();  // return the no content status
    }
}
