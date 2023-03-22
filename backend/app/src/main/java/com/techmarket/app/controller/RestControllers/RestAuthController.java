package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.security.jwt.LoginRequest;
import com.techmarket.app.security.jwt.UserLoginService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class RestAuthController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "400", description = "Invalid username or password")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {
        return userLoginService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    @ApiResponse(responseCode = "200", description = "Logout successful")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh the access token")
    @ApiResponse(responseCode = "200", description = "Access token refreshed")
    public ResponseEntity<AuthResponse> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userLoginService.refresh(refreshToken);
    }

    @PostMapping("/signup")
    @Operation(summary = "Signup")
    @ApiResponse(responseCode = "200", description = "Signup successful")
    @ApiResponse(responseCode = "400", description = "Email already exists")
    // Request and email and a password, the rest of the information will be added later
    public ResponseEntity<AuthResponse> signup(@RequestBody LoginRequest loginRequest){
        // Create a new user
        User user = new User();
        user.setEmail(loginRequest.getUsername());
        user.setEncodedPassword(passwordEncoder.encode(loginRequest.getPassword()));
        // Set default role USER
        user.setRoles(Collections.singletonList("USER"));

        // Save the user
        userService.saveUser(user);

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "User created successfully, you can now login");

        // Return the response
        return ResponseEntity.ok(authResponse);
    }

}
