package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;

import com.techmarket.app.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private ProductRepository productRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Product> items = productRepository.findFirst10ByOrderByProductName();
        if (items.isEmpty()) {
            model.addAttribute("results", null);
        } else {
            model.addAttribute("results", items);
        }

        return "dashboard";
    }
}
