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
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        List<String> finalMessageList = Collections.emptyList();

        for (int cont = 0; cont < messages.size(); cont++){
            Message temp = messages.get(cont);
            finalMessageList.add(temp.getMessage());
        }

        // Return the response
        return finalMessageList;
    }
    @GetMapping("/messages/agent/{id}")
    public ResponseEntity<List<Message>> getAgentMessages(HttpServletRequest request, @PathVariable Long id) {
        // Get the current user
        User user = userService.getCurrentUser(request);
        List<Message> messages = messageService.getMessagesByUserAndAgent(user.getId(), id);

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

    @PostMapping("/send-message/agent/{id}")
    public ResponseEntity<AuthResponse> sendMessageAgent(HttpServletRequest request, Message message, @RequestBody String messageText, @PathVariable Long id) {
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
