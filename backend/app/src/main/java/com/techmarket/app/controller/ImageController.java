package com.techmarket.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageController {

    @PostMapping("/{id}/upload")
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
        // Upload image to database, last one should be a .save() method, then return the location of the image
        return ResponseEntity.created(null).build(); // Returns a 201 Created response, we will change null to the location of the image
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getImage(@PathVariable long id) {
        // Get image from database
        return ResponseEntity.ok().build(); // Returns a 200 OK response, we will change null to the image
        // return ResponseEntity.notFound().build(); // Returns a 404 Not Found response when the image is not found
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable long id) {
        // Delete image from database
        return ResponseEntity.noContent().build(); // Returns a 204 No Content response
    }
}
