package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecommendationService recommendationService;
    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
            // Check if user is logged in
        if (currentUser != null) {
            model.addAttribute("isLoggedIn", true);
            // Check if the user is an admin
            if (currentUser.getRoles().contains("ADMIN")) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
            List<Product> recommended = recommendationService.getRecommendedProducts();
            if(recommended.size() != 0){
                model.addAttribute("default",false);
                for (Product product : recommended) {
                    model.addAttribute("productName", product.getProductName());
                    model.addAttribute("productPrice", product.getProductPrice());
                    model.addAttribute("productId", product.getProductId());
                }
            }
            else{
                model.addAttribute("default",true);
            }

        } else {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("default",true);
        }
        return "index";
    }

}