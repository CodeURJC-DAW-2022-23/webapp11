package com.techmarket.app.controller;

import com.techmarket.app.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.techmarket.app.Repositories.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Controller
public class PrivateController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('USER','AGENT')")
    @GetMapping("/profile")
    public void profile(Model model, Principal user) {

        //We want to fill out the form with the user's information when we load the page
        //First we get the user's email with the auth method
        //Then we use the email to get the user's information from the database
        //Then we fill out the form with the user's information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("firstName", currentUser.getFirstName());
            model.addAttribute("lastName", currentUser.getLastName());
            model.addAttribute("email", currentUser.getEmail());
            model.addAttribute("phoneNumber", currentUser.getPhoneNumber());
            model.addAttribute("address", currentUser.getAddress());
            model.addAttribute("city", currentUser.getCity());
            model.addAttribute("state", currentUser.getState());
            model.addAttribute("area", currentUser.getArea());
            model.addAttribute("country", currentUser.getCountry());
            model.addAttribute("postcode", currentUser.getPostcode());


        } else {
            model.addAttribute("isLoggedIn", false);
        }


    }

    @PreAuthorize("hasAnyAuthority('USER','AGENT')")
    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String address, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String area, @RequestParam(required = false) String country, @RequestParam(required = false) String postcode) {
        // Basic edit profile functionality
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Check for null values
        if (firstName != null) {
            currentUser.setFirstName(firstName);
        }
        if (lastName != null) {
            currentUser.setLastName(lastName);
        }
        if (phoneNumber != null) {
            currentUser.setPhoneNumber(phoneNumber);
        }
        if (address != null) {
            currentUser.setAddress(address);
        }
        if (city != null) {
            currentUser.setCity(city);
        }
        if (state != null) {
            currentUser.setState(state);
        }
        if (area != null) {
            currentUser.setArea(area);
        }
        if (country != null) {
            currentUser.setCountry(country);
        }
        if (postcode != null) {
            currentUser.setPostcode(postcode);
        }
        userRepository.save(currentUser);  // Save the changes to the database
        return "redirect:/profile";
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
