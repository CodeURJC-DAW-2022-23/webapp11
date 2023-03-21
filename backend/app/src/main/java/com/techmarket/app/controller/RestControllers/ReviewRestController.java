package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;

import java.net.URI;
import java.util.List;

import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService ReviewService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long id) {
        List<Review> reviewList = ReviewService.getAllReviewsByProductId(id);
        return ResponseEntity.ok(reviewList);

    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        ReviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();  // return the no content status
    }

    @PostMapping("/create/{productId}")
    public ResponseEntity<AuthResponse> createReview(@PathVariable Long productId, @RequestBody Review review, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        if (user.getPurchasedProducts().contains(productService.getProductById(productId)) && !productService.getProductById(productId).getReviews().contains(user)) {
            Review newReview = ReviewService.createReview(review);
            newReview.setUser(user);
            newReview.setProduct(productService.getProductById(productId));
            ReviewService.saveReview(newReview);
            productService.getProductById(productId).getReviews().add(newReview);
            productService.saveProduct(productService.getProductById(productId));
            AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS,newReview.getReviewId().toString());
            return ResponseEntity.ok(authResponse);
        }
        else{
            AuthResponse authResponse =  new AuthResponse(AuthResponse.Status.FAILURE,"Error");
            return ResponseEntity.ok(authResponse); //change the return with AuthResponse

        }
    }

}