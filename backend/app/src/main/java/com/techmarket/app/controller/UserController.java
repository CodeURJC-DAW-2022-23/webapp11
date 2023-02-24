package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import com.techmarket.app.security.EncoderConfiguration;
import com.techmarket.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.mail.MessagingException;
import java.util.ArrayList;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncoderConfiguration passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Sign up page
    @GetMapping("/signup")
    public String signup() {
        return "register";
    }

    // Sign in page
    @GetMapping("/signin")
    public String signin() {
        return "login";
    }


    // Sign up a new user
    @PostMapping("/signup-user")
    public String signupuser(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) throws MessagingException {
        User user = new User( email,  firstName, lastName);
        user.setEncodedPassword(passwordEncoder.passwordEncoder().encode(password));
        if (userRepository.findByEmail(email) != null) {
            // User already exists, 409 Conflict
            return "redirect:/signup?error=409";
        }
        userRepository.save(user);
        // Check if the user has successfully signed up by checking the response code to send them the confirmation email
        if (userRepository.findByEmail(email) != null) {
            // User created, 201 Created
            emailService.sendAccountConfirmationEmail(email, firstName);
            return "redirect:/signin?success=201";  // Redirect to sign in page
        } else {
            // User not created, 400 Bad Request
            return "redirect:/signup?error=400";
        }
        // If there's information missing and the user can't be created, the response will be 400 Bad Request, Spring will handle that

    }


    // Sign in an existing user (this is handled by Spring)


}
