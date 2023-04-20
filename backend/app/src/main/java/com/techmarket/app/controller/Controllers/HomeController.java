package com.techmarket.app.controller.Controllers;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.RecommendationService;
import com.techmarket.app.service.UserService;
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
    private RecommendationService recommendationService;



    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
            // Check if user is logged in
        if (currentUser != null) {
            // Check if the user is an admin
            if (currentUser.getRoles().contains("ADMIN")) {
                // Get 4 random products
                List<Product> products = productService.getRandomProducts();
                model.addAttribute("products", products);
            } else {
                List<Product> recommended = recommendationService.getRecommendedProducts();
                if(recommended.size() != 0){
                    model.addAttribute("products", recommended);
                }
                else {
                    List<Product> products = productService.getRandomProducts();
                    model.addAttribute("products", products);
                }
            }

        } else {
            // Get the first 4 products, not tailored to the user
            List<Product> products = productService.getRandomProducts();
            model.addAttribute("products", products);
        }
        return "home";
    }

}