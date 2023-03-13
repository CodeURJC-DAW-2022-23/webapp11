package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.User;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
