package com.techmarket.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.techmarket.app.Repositories.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        model.addAttribute("fname", name);
        model.addAttribute("lname", name);
        // TODO: Model this
        //CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); if we start using the tokens
        return "profile";
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        // Check if the user is an admin
        if (request.isUserInRole("ADMIN")) {
            String name = request.getUserPrincipal().getName();
            model.addAttribute("fname", name);
            model.addAttribute("lname", name);
            // TODO: Model this
            return "admin";
        }
        return "redirect:/";  // Redirect to the home page, we will send the user to a 403 (Forbidden) page later
    }
}
