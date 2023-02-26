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
public class WishlistController {

    @Autowired
    private UserRepository UserRepository;

    @GetMapping("/wishlist")
    public String wishlist(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        if (user.getWishlist().isEmpty()){
            model.addAttribute("items", null);
        } else {
            model.addAttribute("items", user.getWishlist());
        }

        return "wishlist";

    }



    @GetMapping("/add-to-wishlist")
    public String addToCart(int productId) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        // Add the product to the wishlist
        user.getWishlist().add(new Product(productId));  // This will add the product to the cart, but it will not be a product from the database, it will be a product with only the productId, so it will not have the product name, price, etc.
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }

    @GetMapping("/remove-from-wishlist")
    public String removeFromCart(int productId) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = UserRepository.findByEmail(email);
        // Remove the product from the wishlist
        user.getWishlist().remove(new Product(productId));
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }

}
