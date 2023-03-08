package com.techmarket.app.controller.Controllers;


import com.techmarket.app.model.User;
import com.techmarket.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class HeaderController {



    @Autowired
    private UserService userService;

    // Get the header on every page
    @ModelAttribute("header")
    public String header(Model model) {
        // get user from session
        User user = userService.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
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
