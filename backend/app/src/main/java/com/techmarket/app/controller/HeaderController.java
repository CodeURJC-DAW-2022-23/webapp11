package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class HeaderController {

    @Autowired
    private UserRepository userRepository;

    // Get the header on every page
    @ModelAttribute("header")
    public String header(Model model) {
        // get user from session
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
            if (user.getRoles().contains("ADMIN")) {
                model.addAttribute("isAdmin", true);
            } else if (user.getRoles().contains("AGENT")) {
                model.addAttribute("isAgent", true);
            } else {
                model.addAttribute("isUser", true);
            }
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "header";
    }
}
