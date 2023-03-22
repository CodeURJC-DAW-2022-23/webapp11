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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

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

        return ResponseEntity.ok().body("Profile picture updated");
    }

    // Delete the user's profile picture
    @DeleteMapping("/profile-picture")
    @Operation(summary = "Delete the user's profile picture")
    @ApiResponse(responseCode = "200", description = "Profile picture deleted")
    @ApiResponse(responseCode = "400", description = "User not found")
    public ResponseEntity<Object> deleteProfilePicture(HttpServletRequest request) throws SQLException {
        User user = userService.getCurrentUser(request);
        Long imageId = user.getProfilePicture().getImageId();
        user.setProfilePicture(null);
        userService.saveUser(user);
        imageService.deleteImage(imageId);


        return ResponseEntity.ok().body("Profile picture deleted");
    }

    @PostMapping("/{id}/main-image")
    public ResponseEntity<Object> uploadProductMainImage(@RequestParam("image") MultipartFile image, @PathVariable long id) throws SQLException, IOException {
        Product product = productService.getProductById(id);
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }
        Image mainImage = new Image();
        mainImage.setFileName(image.getOriginalFilename());
        Blob blob = new SerialBlob(image.getBytes());
        mainImage.setImageBlob(blob);
        product.setMainImage(mainImage);

        imageService.saveImage(mainImage);
        productService.saveProduct(product);

        return ResponseEntity.ok().body("Product main Image updated");
    }

    @PostMapping("/{id}/product-image")
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

        return ResponseEntity.ok().body("Product images updated");
    }

    @PostMapping("/{id}/review-image")
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

        return ResponseEntity.ok().body("Review images updated");
    }

    @DeleteMapping("/{id}/main-image")
    public ResponseEntity<Object> deleteProductMainImage(@PathVariable long id) throws SQLException{
        Product product =productService.getProductById(id);
        Long imageId = product.getMainImage().getImageId();
        product.setMainImage(null);
        productService.saveProduct(product);
        imageService.deleteImageById(imageId);

        return ResponseEntity.ok().body("Product main image deleted");
    }

    @DeleteMapping("/{id}/images")
    public ResponseEntity<Object> deleteProductImages(@PathVariable long id) throws SQLException{
        Product product = productService.getProductById(id);
        List<Image> imageList = product.getImages();
        product.setImages(null);
        productService.saveProduct(product);
        imageService.deleteAllImages(imageList);

        return ResponseEntity.ok().body("Product images deleted");
    }

    @DeleteMapping("/{id}/review-image")
    public ResponseEntity<Object> deleteReviewImage(@PathVariable long id){
        Review review = reviewService.getReviewById(id);
        List<Image> imageList = review.getImages();
        review.setImages(null);
        reviewService.saveReview(review);
        imageService.deleteAllImages(imageList);

        return ResponseEntity.ok().body("Review images deleted");
    }


}
