package com.techmarket.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.techmarket.app.Repositories.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('USER','AGENT')")
    @GetMapping("/profile")
    public void profile(){

        //We want to fill out the form with the user's information when we load the page
        //First we get the user's email with the auth method
        //Then we use the email to get the user's information from the database
        //Then we fill out the form with the user's information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

       //TODO: Model this
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
