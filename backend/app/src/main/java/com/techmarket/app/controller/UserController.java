package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import com.techmarket.app.security.EncoderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncoderConfiguration passwordEncoder;

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
    public ResponseEntity<User> signupuser(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) {
        User user = new User( email,  firstName, lastName);
        user.setEncodedPassword(passwordEncoder.passwordEncoder().encode(password));
        if (userRepository.findByEmail(email) != null) {
            // User already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict, we can return something meaningful and not a blank screen
        }
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED); // 201 Created, this will also return the user object in the response body
        // If there's information missing and the user can't be created, the response will be 400 Bad Request, Spring will handle that
    }


    // Sign in an existing user (this is handled by Spring)


}
