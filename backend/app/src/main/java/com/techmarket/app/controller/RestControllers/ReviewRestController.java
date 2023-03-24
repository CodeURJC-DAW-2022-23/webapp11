package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Review;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "Get all reviews of a product", description = "Get all reviews of a product", tags = {"reviews"})
    @ApiResponse(responseCode = "200", description = "successful operation")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long id) {
        List<Review> reviewList = ReviewService.getAllReviewsByProductId(id);
        return ResponseEntity.ok(reviewList);

    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a review", description = "Delete a review", tags = {"reviews"})
    @ApiResponse(responseCode = "204", description = "Review removed")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        ReviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();  // return the no content status
    }

    @PostMapping("/create/{productId}")
    @Operation(summary = "Create a review", description = "Create a review", tags = {"reviews"})
    @ApiResponse(responseCode = "200", description = "Review created")
    @ApiResponse(responseCode = "404", description = "Product not found")
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