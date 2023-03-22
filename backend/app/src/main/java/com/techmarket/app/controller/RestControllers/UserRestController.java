
package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Message;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.MessageService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/messages")
    public List<String> getMessages(HttpServletRequest request) {
        // Get the current user
        User user = userService.getCurrentUser(request);
        List<Message> messages = messageService.getMessageById(user.getId());
        List<String> finalMessageList = new ArrayList<>();
        for (Message temp : messages) {
            finalMessageList.add(temp.getMessage());
        }
        // Return the response
        return finalMessageList;
    }

    @GetMapping("/messages/agent/{id}")
    public List<String> getAgentMessages(HttpServletRequest request, @PathVariable Long id) {
        // Get the current user
        User user = userService.getCurrentUser(request);

        List<Message> messages = messageService.getMessageById(id);
        List<String> finalMessageList = new ArrayList<>();
        for (Message temp : messages) {
            finalMessageList.add(temp.getMessage());
        }
        // Return the response
        return finalMessageList;
    }

    @GetMapping("/profile")
    @Operation(summary = "Get the current user's profile")
    @ApiResponse(responseCode = "200", description = "User profile retrieved")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        // Get the current user
        User user = userService.getCurrentUser(request);

        user.setEncodedPassword("Redacted for security reasons");
        user.setToken(null);
        user.setPasswordChangeToken(null);
        user.setWishlist(Collections.emptyList());
        user.setShoppingCart(Collections.emptyList());
        user.setPurchasedProducts(Collections.emptyList());

        // Return the response
        return ResponseEntity.ok(user);
    }

    @PostMapping("/send-message")
    public ResponseEntity<AuthResponse> sendMessage(HttpServletRequest request, @RequestBody String
            messageText, Message message) {
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

    @PostMapping("/send-message/agent/{id}")
    public ResponseEntity<AuthResponse> sendMessageAgent(HttpServletRequest request, Message
            message, @RequestBody String messageText, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser(request);
        // Append "Name: " to the message
        User user = userService.getUserById(id);
        message.setAgent(currentUser);
        message.setUser(user);
        message.setMessage("Agent: " + messageText);
        messageService.saveMessage(message);

        // Create a response object
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Message sent successfully");

        // Return the response
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/profile")
    @Operation(summary = "Update the current user's profile")
    @ApiResponse(responseCode = "200", description = "User profile updated")
    public ResponseEntity<User> updateUser(HttpServletRequest request, @RequestBody User user) {
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

        currentUser.setEncodedPassword("Redacted for security reasons");
        currentUser.setToken(null);
        currentUser.setPasswordChangeToken(null);
        user.setWishlist(Collections.emptyList());
        user.setShoppingCart(Collections.emptyList());
        user.setPurchasedProducts(Collections.emptyList());

        // Return the response
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/purchase-history")
    @Operation(summary = "Get the current user's purchase history")
    @ApiResponse(responseCode = "200", description = "User purchase history retrieved")
    public ResponseEntity<Page<Purchase>> getPurchaseHistory(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Get the current user
        User user = userService.getCurrentUser(request);

        // Get the user's purchase history
        Page<Purchase> purchaseHistory = userService.getPurchaseHistory(user, page);

        // Return the response
        return ResponseEntity.ok(purchaseHistory);
    }
}
