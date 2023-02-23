package com.techmarket.app.controller;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import jakarta.websocket.Session;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        // Access the user's cart using the session
        User user = (User) ((Authentication) principal).getPrincipal();
        // Check if the cart is empty
        if (user.getShoppingCart().isEmpty()) {
            model.addAttribute("items", true);
            // Model the cart using the products from the database
            model.addAttribute("items", user.getShoppingCart();
           // Calculate the total price of the cart
            double totalPrice = 0;
            for (Product product : user.getShoppingCart()) {
                totalPrice += product.getProductPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("items", false);
        }
        return "cart";
    }
}
