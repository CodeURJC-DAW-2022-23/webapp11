package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.security.jwt.LoginRequest;
import com.techmarket.app.security.jwt.UserLoginService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RestAuthController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {
        return userLoginService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
    }

    @GetMapping("/user")
    public ResponseEntity<AuthResponse> getUser(HttpServletRequest request) {
        // Get the current user
        User user = userService.getCurrentUser(request);

        // Create a map containing the user information
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("email", user.getEmail());
        userInfo.put("name", user.getFirstName() + " " + user.getLastName());
        userInfo.put("roles", user.getRoles());

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, userInfo.toString());

        // Return the response
        return ResponseEntity.ok(authResponse);
    }

}
