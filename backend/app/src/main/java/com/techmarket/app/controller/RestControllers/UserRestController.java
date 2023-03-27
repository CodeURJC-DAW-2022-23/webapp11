
package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Message;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.MessageService;
import com.techmarket.app.service.PDFService;
import com.techmarket.app.service.PurchaseService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @Autowired
    private PDFService pdfService;


    @GetMapping("/messages")
    @Operation(summary = "Get the current user's messages")
    @ApiResponse(responseCode = "200", description = "User messages retrieved")
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
    @Operation(summary = "Get the current agent's messages that are associated with the id of the user he is talking to")
    @ApiResponse(responseCode = "200", description = "Agent messages retrieved")
    @ApiResponse(responseCode = "400", description = "User not found")
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
    @Operation(summary = "Send a message from the current user")
    @ApiResponse(responseCode = "200", description = "Message sent")
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
    @Operation(summary = "Send a message from the current agent to the id of the user he is talking to")
    @ApiResponse(responseCode = "200", description = "Message sent")
    @ApiResponse(responseCode = "400", description = "User not found")
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



    @PostMapping("/checkout")
    @Operation(summary = "Checkout the current user's shopping cart")
    @ApiResponse(responseCode = "200", description = "User checked out")
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

    @PostMapping("/return-purchase/{purchaseId}")
    @Operation(summary = "Return a purchase")
    @ApiResponse(responseCode = "200", description = "Purchase returned")
    @ApiResponse(responseCode = "400", description = "Purchase not found")
    public ResponseEntity<AuthResponse> returnPurchase(HttpServletRequest request, @PathVariable Long purchaseId) {
        // Get the current user
        User currentUser = userService.getCurrentUser(request);
        Purchase purchase = purchaseService.getPurchaseById(purchaseId);
        if(Objects.equals(purchase.getUser().getId(), currentUser.getId()) && !purchase.isCancelled()){
            purchase.setCancelled(true);
            purchaseService.savePurchase(purchase);
            AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Purchase returned successfully");
            return ResponseEntity.ok(authResponse);
        }
        else {
            AuthResponse authResponse = new AuthResponse(AuthResponse.Status.FAILURE, "Purchase return failed");
            return ResponseEntity.ok(authResponse);
        }
    }

    @GetMapping("/purchase/generate-pdf/{purchaseId}")
    @Operation(summary = "Generate a pdf for a purchase")
    @ApiResponse(responseCode = "200", description = "Purchase returned")
    @ApiResponse(responseCode = "400", description = "Purchase not found")
    public ResponseEntity<?> generatePDF(HttpServletRequest request, @PathVariable long purchaseId, HttpServletResponse
            response) throws IOException {
        User currentUser = userService.getCurrentUser(request);
        Purchase purchase = purchaseService.getPurchaseById(purchaseId);
        if (currentUser.getPurchasedProducts().contains(purchase.getProduct()) && !purchase.isCancelled()) {
            PDFService.generateInvoice(response,purchase);
            return ResponseEntity.ok("PDF generated successfully");
        }

        return ResponseEntity.ok("PDF generation failed");
    }

}
