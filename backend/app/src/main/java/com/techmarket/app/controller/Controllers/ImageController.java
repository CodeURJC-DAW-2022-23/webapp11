package com.techmarket.app.controller.Controllers;

import com.techmarket.app.Repositories.ImageRepository;
import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.ReviewRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
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
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/{id}/upload")
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
        // Upload image to database, last one should be a .save() method, then return the location of the image
        return ResponseEntity.created(null).build(); // Returns a 201 Created response, we will change null to the location of the image
    }

    @GetMapping("/{id}/userpfp")
    public ResponseEntity<Object> getImage(HttpServletResponse response, @PathVariable long id) throws SQLException, IOException {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
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
        if (productRepository.findById(productId).isPresent()) {
            // Get the InputStreamResource from the database
            InputStreamResource file = new InputStreamResource(productRepository.findById(productId).get().getMainImage().getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(productRepository.findById(productId).get().getMainImage().getImageBlob().length())
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}/images/{index}")  // Image from the image list of a product
    public ResponseEntity<Object> getImages(@PathVariable long productId, @PathVariable int index) throws SQLException, IOException {
        if (productRepository.findById(productId).isPresent()) {
            index-=1;
            InputStreamResource file = new InputStreamResource(productRepository.findById(productId).get().getImages().get(index).getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(productRepository.findById(productId).get().getImages().get(index).getImageBlob().length())
                    .body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/review/{reviewId}/imagerev/{index}")  // Image from the image list of a product
    public ResponseEntity<Object> getImagesReview(@PathVariable int index, @PathVariable long reviewId) throws SQLException, IOException {
        if (reviewRepository.findById(String.valueOf(reviewId)).isPresent()) {
            index-=1;
            InputStreamResource file = new InputStreamResource(reviewRepository.findById(String.valueOf(reviewId)).get().getImages().get(index).getImageBlob().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/png")
                    .contentLength(reviewRepository.findById(String.valueOf(reviewId)).get().getImages().get(index).getImageBlob().length())
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
