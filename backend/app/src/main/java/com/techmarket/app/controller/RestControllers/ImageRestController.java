package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Image;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.UserService;
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

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    // Get the user's profile picture
    @GetMapping("/profile-picture")
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
    public ResponseEntity<Object> deleteProfilePicture(HttpServletRequest request) throws SQLException {
        User user = userService.getCurrentUser(request);
        Long imageId = user.getProfilePicture().getImageId();
        user.setProfilePicture(null);
        userService.saveUser(user);
        imageService.deleteImage(imageId);


        return ResponseEntity.ok().body("Profile picture deleted");
    }
}
