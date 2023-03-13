package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Message;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.security.jwt.MessageRequest;
import com.techmarket.app.service.MessageService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(HttpServletRequest request) {
        // Get the current user
        User user = userService.getCurrentUser(request);
        List<Message> messages = messageService.getMessagesByUser(user.getId());

        // Return the response
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/profile")
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

    @PostMapping("/send-message")
    public ResponseEntity<AuthResponse> sendMessage(HttpServletRequest request, @RequestBody String messageText, Message message) {
        User currentUser = userService.getCurrentUser(request);
        // Append "Name: " to the message
        message.setMessage(currentUser.getFirstName() + ": " + messageText);
        message.setUser(currentUser);
        messageService.saveMessage(message);

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Message posted successfully");

        // Return the response
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/send-message/agent")
    public ResponseEntity<AuthResponse> sendMessageAgent(HttpServletRequest request, Message message, @RequestBody MessageRequest messageRequest) {
        User currentUser = userService.getCurrentUser(request);
        // Append "Name: " to the message
        String messageText = "Agent: " + messageRequest.getMessage();
        User user = userService.getUserById(messageRequest.getId());
        message.setAgent(currentUser);
        message.setUser(user);
        message.setMessage(messageText);
        messageService.saveMessage(message);

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "User information updated successfully");

        // Return the response
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<AuthResponse> updateUser(HttpServletRequest request, @RequestBody User user) {
        // Get the current user
        User currentUser = userService.getCurrentUser(request);

        // Update the user information
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setArea(user.getArea());
        currentUser.setAddress(user.getAddress());
        currentUser.setCity(user.getCity());
        currentUser.setCountry(user.getCountry());
        currentUser.setPostcode(user.getPostcode());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setProfilePicture(user.getProfilePicture()); // I still have to figure out how to handle the images

        // Save the user
        userService.saveUser(currentUser);

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "User information updated successfully");

        // Return the response
        return ResponseEntity.ok(authResponse);
    }
}
