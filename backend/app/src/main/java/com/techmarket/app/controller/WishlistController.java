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

        }

        return "wishlist";

    }

}
