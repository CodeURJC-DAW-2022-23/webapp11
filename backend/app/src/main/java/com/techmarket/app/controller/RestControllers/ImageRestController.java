package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Image;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    // Get the user's profile picture
    @GetMapping("/profile-picture")
    @Operation(summary = "Get the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture retrieved")
    @ApiResponse(responseCode = "400", description = "User not found")
    public ResponseEntity<Object> downloadProfilePicture(HttpServletRequest request) throws SQLException {
        // Get the current user
        User user = userService.getCurrentUser(request);

        InputStreamResource resource = new InputStreamResource(user.getProfilePicture().getImageBlob().getBinaryStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getProfilePicture().getFileName() + "\"")
                .body(resource);
    }

    // Update the user's profile picture
    @PostMapping("/profile-picture")
    @Operation(summary = "Update the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture updated")
    @ApiResponse(responseCode = "400", description = "Please upload a file")
    public ResponseEntity<Object> uploadProfilePicture(@RequestParam("image") MultipartFile image, HttpServletRequest request) throws SQLException, IOException {
        User user = userService.getCurrentUser(request);
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        Image profilePicture = new Image();
        profilePicture.setFileName(image.getOriginalFilename());
        Blob blob = new SerialBlob(image.getBytes());
        profilePicture.setImageBlob(blob);
        user.setProfilePicture(profilePicture);

        imageService.saveImage(profilePicture);
        userService.saveUser(user);

        // Return the location of the new profile picture and tell the user he has updated his profile picture
        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile picture updated");
        response.put("location", "/api/images/profile-picture");

        // Return the location of the image on the header
        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/api/images/profile-picture").body(response);
    }

    // Delete the user's profile picture
    @DeleteMapping("/profile-picture")
    @Operation(summary = "Delete the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture deleted")
    public ResponseEntity<Object> deleteProfilePicture(HttpServletRequest request) throws SQLException {
        User user = userService.getCurrentUser(request);
        Long imageId = user.getProfilePicture().getImageId();
        user.setProfilePicture(null);
        userService.saveUser(user);
        imageService.deleteImage(imageId);


        return ResponseEntity.ok().body("Profile picture deleted");
    }

    @GetMapping("/{id}/main-image")
    @Operation(summary = "Get the product's main image")
    @ApiResponse(responseCode = "200", description = "Main image retrieved")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> getProductMainImage(@PathVariable long id) throws SQLException {
        Product product = productService.getProductById(id);
        InputStreamResource resource = new InputStreamResource(product.getMainImage().getImageBlob().getBinaryStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getMainImage().getFileName() + "\"")
                .body(resource);
    }

    @PostMapping("/{id}/main-image")
    @PostAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update the product's main image")
    @ApiResponse(responseCode = "200", description = "Main image updated")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> uploadProductMainImage(@RequestParam("image") MultipartFile image, @PathVariable long id) throws SQLException, IOException {
        Product product = productService.getProductById(id);
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        Image img = new Image();
        img.setFileName(image.getOriginalFilename());
        Blob blob = new SerialBlob(image.getBytes());
        img.setImageBlob(blob);
        product.setMainImage(img);

        imageService.saveImage(img);
        productService.saveProduct(product);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Main image updated");
        response.put("location", "/api/images/" + id + "/main-image");

        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/api/images/" + id + "/main-image").body(response);
    }

    @GetMapping("/{id}/product-image")
    @Operation(summary = "Get the product's images")
    @ApiResponse(responseCode = "200", description = "Product images retrieved")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> getProductImages(@PathVariable long id) throws SQLException {
        Product product = productService.getProductById(id);
        List<Image> images = product.getImages();
        return ResponseEntity.ok().body(images);
    }

    @PostMapping("/{id}/product-image")
    @PostAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update the product's images (add new images)")
    @ApiResponse(responseCode = "200", description = "Product image added")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> uploadProductImage(@RequestParam("image") MultipartFile image, @PathVariable long id) throws SQLException, IOException {
        Product product = productService.getProductById(id);
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        Image img = new Image();
        img.setFileName(image.getOriginalFilename());
        Blob blob = new SerialBlob(image.getBytes());
        img.setImageBlob(blob);
        product.getImages().add(img);

        imageService.saveImage(img);
        productService.saveProduct(product);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product image added");
        response.put("location", "/api/images/" + id + "/product-image");

        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/api/images/" + id + "/product-image").body(response);
    }

    @PostMapping("/{id}/review-image")
    @PostAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update the review's images (add new images)")
    @ApiResponse(responseCode = "200", description = "Review image added")
    @ApiResponse(responseCode = "400", description = "Review not found")
    public ResponseEntity<Object> uploadReviewImage(@RequestParam("image") MultipartFile image, @PathVariable long id) throws SQLException, IOException {
        Review review = reviewService.getReviewById(id);
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        Image img = new Image();
        img.setFileName(image.getOriginalFilename());
        Blob blob = new SerialBlob(image.getBytes());
        img.setImageBlob(blob);
        review.getImages().add(img);

        imageService.saveImage(img);
        reviewService.saveReview(review);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Review image added");
        response.put("location", "/api/images/" + id + "/review-image");

        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/api/images/" + id + "/review-image").body(response);
    }

    @DeleteMapping("/d/{id}/main-image")
    @Operation(summary = "Delete the product's main image")
    @ApiResponse(responseCode = "200", description = "Main image deleted")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> deleteProductMainImage(@PathVariable long id) throws SQLException{
        Product product =productService.getProductById(id);
        Long imageId = product.getMainImage().getImageId();
        product.setMainImage(null);
        productService.saveProduct(product);
        imageService.deleteImageById(imageId);

        return ResponseEntity.ok().body("Product main image deleted");
    }

    @DeleteMapping("/d/{id}/images")
    @Operation(summary = "Delete the product's images")
    @ApiResponse(responseCode = "200", description = "Product images deleted")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<Object> deleteProductImages(@PathVariable long id) throws SQLException{
        Product product = productService.getProductById(id);
        List<Image> imageList = product.getImages();
        product.setImages(Collections.emptyList());
        productService.saveProduct(product);
        imageService.deleteAllImages(imageList);

        return ResponseEntity.ok().body("Product images deleted");
    }

    @DeleteMapping("/d/{id}/review-image")
    @Operation(summary = "Delete the review's images")
    @ApiResponse(responseCode = "200", description = "Review images deleted")
    @ApiResponse(responseCode = "400", description = "Review not found")
    public ResponseEntity<Object> deleteReviewImage(@PathVariable long id){
        Review review = reviewService.getReviewById(id);
        List<Image> imageList = review.getImages();
        review.setImages(Collections.emptyList());
        reviewService.saveReview(review);
        imageService.deleteAllImages(imageList);

        return ResponseEntity.ok().body("Review images deleted");
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "Get the image by id")
    @ApiResponse(responseCode = "200", description = "Image retrieved")
    @ApiResponse(responseCode = "400", description = "Image not found")
    public ResponseEntity<Object> getImageById(@PathVariable long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        InputStreamResource resource = new InputStreamResource(image.getImageBlob().getBinaryStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }




}
