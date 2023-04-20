package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Image;
import com.techmarket.app.model.Review;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    @Autowired
    private ReviewService ReviewService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;

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
    public ResponseEntity<Object> createReview(@PathVariable Long productId, @RequestParam("rating") int rating, @RequestParam("reviewTitle") String reviewTitle, @RequestParam("reviewText") String reviewText, @RequestParam("images") MultipartFile[] images, HttpServletRequest request) throws IOException, SQLException {
        User user = userService.getCurrentUser(request);
        if (user.getPurchasedProducts().contains(productService.getProductById(productId)) && !productService.getProductById(productId).getReviews().contains(user)) {
            Review newReview = new Review();
            newReview.setRating(rating);
            newReview.setReviewTitle(reviewTitle);
            newReview.setReviewText(reviewText);
            newReview.setProduct(productService.getProductById(productId));
            newReview.setUser(user);
            ArrayList<Image> imgList = new ArrayList<>();
            for (MultipartFile img : images) {
                Image newImage = new Image();
                newImage.setFileName(img.getOriginalFilename());
                newImage.setImageBlob(new SerialBlob(img.getBytes()));
                imageService.saveImage(newImage);
                imgList.add(newImage);
            }
            newReview.setImages(imgList);
            ReviewService.saveReview(newReview);
            // Return the location of the new resource on the response header
            return ResponseEntity.ok().header("Location", "/api/reviews/get/" + newReview.getReviewId()).body(newReview);
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("message", "You can't review this product"));
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a review", description = "Get a review", tags = {"reviews"})
    @ApiResponse(responseCode = "200", description = "Review found")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<Review> getReview(@PathVariable Long id) {
        Review review = ReviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }


    @GetMapping("{id}/user-email")
    @Operation(summary = "Get the user of a review", description = "Get the user of a review", tags = {"reviews"})
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<String> getReviewUser(@PathVariable Long id) {
        Review review = ReviewService.getReviewById(id);
        return ResponseEntity.ok(review.getUser().getEmail());
    }
    @GetMapping("/pfp/{email}")
    @Operation(summary = "Get the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture retrieved")
    @ApiResponse(responseCode = "400", description = "User not found")
    public ResponseEntity<Object> getUserProfilePicture(@PathVariable String email) throws SQLException {
        User user = userService.getUserName(email);
        Image image = user.getProfilePicture();
        InputStreamResource resource = new InputStreamResource(image.getImageBlob().getBinaryStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


    @GetMapping("/{reviewId}/user/pfp")
    @Operation(summary = "Get the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture retrieved")
    @ApiResponse(responseCode = "400", description = "User not found")
    public ResponseEntity<?> getUserProfilePictureFromReview(HttpServletRequest request,@PathVariable long reviewId) throws SQLException {
        Review review = ReviewService.getReviewById(reviewId);
        User user = review.getUser();
        Image image = user.getProfilePicture();
        long id = image.getImageId();

        return ResponseEntity.ok(id);

    }




}