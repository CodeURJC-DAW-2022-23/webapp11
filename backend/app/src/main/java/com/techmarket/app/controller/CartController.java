package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
public class CartController {

    @Autowired
    private UserRepository UserRepository;

    @GetMapping("/cart")
    public String cart(Model model) {
        // Access the user's cart using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        // Check if the cart is empty
        if (!user.getShoppingCart().isEmpty()) {
            // Model the cart using the products from the database
            model.addAttribute("items", user.getShoppingCart());  // This will be a list of products, Mustache will loop through the list and display the products, or at least it should
            // Calculate the total price of the cart
            double totalPrice = 0;
            for (Product product : user.getShoppingCart()) {
                totalPrice += product.getProductPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("items", null);
        }
        return "cart";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(int productId) {
        // Access the user's cart using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        // Add the product to the cart
        user.getShoppingCart().add(new Product(productId));  // This will add the product to the cart, but it will not be a product from the database, it will be a product with only the productId, so it will not have the product name, price, etc.
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }

    @GetMapping("/remove-from-cart")
    public String removeFromCart(int productId) {
        // Access the user's cart using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = UserRepository.findByEmail(email);
        // Remove the product from the cart
        user.getShoppingCart().remove(new Product(productId));
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }
}
