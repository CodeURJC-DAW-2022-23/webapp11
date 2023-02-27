package com.techmarket.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ErrorController {
    // Map error and 404 pages
    @GetMapping("/error")
    public String someError(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "error";
    }

    @GetMapping("/access-denied")
    public String accessdenied(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "accessdenied";
    }
}
