package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;

import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Product> items = productRepository.findAll();
        if (items.isEmpty()) {
            model.addAttribute("item", null);
        } else {
            model.addAttribute("item", items);
        }

        return "dashboard";
    }

    @GetMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
}
