package com.techmarket.app.controller.Controllers;




import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ImageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{id}/upload")
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
        // Upload image to database, last one should be a .save() method, then return the location of the image
        return ResponseEntity.created(null).build(); // Returns a 201 Created response, we will change null to the location of the image
    }

    @GetMapping("/{id}/userpfp")
    public ResponseEntity<Object> getImage(HttpServletResponse response, @PathVariable long id) throws SQLException, IOException {
        if (userService.getUserById(id) != null) {
            User user = userService.getUserById(id);
            if (user.getProfilePicture() != null) {
                // Get the InputStreamResource from the database
                InputStreamResource file = new InputStreamResource(user.getProfilePicture().getImageBlob().getBinaryStream());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(user.getProfilePicture().getImageBlob().length())
                        .body(file);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}/image")  // Main image for a product
    public ResponseEntity<Object> getImage(@PathVariable long productId) throws SQLException, IOException {
        if (productService.getProductById(productId) != null) {
            // Get the InputStreamResource from the database
            InputStreamResource file = new InputStreamResource(productService.getProductById(productId).getMainImage().getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(productService.getProductById(productId).getMainImage().getImageBlob().length())
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}/images/{index}")  // Image from the image list of a product
    public ResponseEntity<Object> getImages(@PathVariable long productId, @PathVariable int index) throws SQLException, IOException {
        if (productService.getProductById(productId) != null) {
            index-=1;
            InputStreamResource file = new InputStreamResource(productService.getProductById(productId).getImages().get(index).getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(productService.getProductById(productId).getImages().get(index).getImageBlob().length())
                    .body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/review/{reviewId}/imagerev/{index}")  // Image from the image list of a product
    public ResponseEntity<Object> getImagesReview(@PathVariable int index, @PathVariable long reviewId) throws SQLException, IOException {
        if (reviewService.getReviewById(reviewId) != null) {
            index-=1;
            InputStreamResource file = new InputStreamResource(reviewService.getReviewById(reviewId).getImages().get(index).getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(reviewService.getReviewById(reviewId).getImages().get(index).getImageBlob().length())
                    .body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable long id) {
        // Delete image from database
        return ResponseEntity.noContent().build(); // Returns a 204 No Content response
    }


}
