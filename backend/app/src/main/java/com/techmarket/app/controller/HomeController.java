package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    private ProductRepository productRepository;
    @Autowired
    private RecommendationService recommendationService;
    @GetMapping("/")
    public String home(Model model, Principal user) {  // Using Principal to check if user is logged in
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
            List<Product> recommended = recommendationService.getRecommendedProducts();
            if(recommended.size() != 0){
                model.addAttribute("default",false);
                for(int i = 0;i<recommended.size();i++) {
                    model.addAttribute("productName", recommended.get(i).getProductName());
                    model.addAttribute("productPrice", recommended.get(i).getProductPrice());
                    //model.addAttribute("productImage",recommended.get(i).getMainImage());
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