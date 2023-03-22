
package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Message;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.security.jwt.MessageRequest;
import com.techmarket.app.service.MessageService;
import com.techmarket.app.service.PurchaseService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private PurchaseService purchaseService;


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


    @PostMapping("/checkout")
    public ResponseEntity<AuthResponse> checkout(HttpServletRequest request, @RequestBody String address) {
        // Get the current user
        User currentUser = userService.getCurrentUser(request);
        List<Product> cart = currentUser.getShoppingCart();
        if(cart.size() > 0 && cart.stream().allMatch(product -> product.getProductStock() > 0)){
            for (Product product : cart) {
                product.setProductStock(product.getProductStock() - 1);
                if(!currentUser.getPurchasedProducts().contains(product)) {
                    currentUser.getPurchasedProducts().add(product);
                }
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                Purchase purchase = new Purchase();
                purchase.setProduct(product);
                purchase.setUser(currentUser);
                purchase.setCancelled(false);
                purchase.setPaymentMethod("Cash on delivery");
                purchase.setTimestamp(year + "-" + month + "-" + day);
                purchase.setTimestamp(address);
                purchaseService.savePurchase(purchase);
            }
            currentUser.getShoppingCart().clear();
            userService.saveUser(currentUser);
           AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Checkout successful");
           return ResponseEntity.ok(authResponse);
        }
        else {
            AuthResponse authResponse = new AuthResponse(AuthResponse.Status.FAILURE, "Checkout failed");
            return ResponseEntity.ok(authResponse);
        }

    }
}
