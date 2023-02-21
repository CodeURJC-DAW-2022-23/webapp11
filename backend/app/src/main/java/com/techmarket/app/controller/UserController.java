package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get the user profile
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

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
        User user = new User(email, passwordEncoder.encode(password), firstName, lastName);
        if (userRepository.findByEmail(email) != null) {
            // User already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict, we can return something meaningful and not a blank screen
        }
        // Create new user, add the default role
        user.setRole(Collections.singletonList("USER"));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED); // 201 Created, this will also return the user object in the response body
        // If there's information missing and the user can't be created, the response will be 400 Bad Request, Spring will handle that
    }

    // Sign in an existing user
    @PostMapping("/signin-user")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {  // IDs on the HTML must match the parameters here
        // Check if the user exists in the database
        User userInDb = userRepository.findByEmail(email);
        if (userInDb == null) {
            // User doesn't exist
            return new ModelAndView("redirect:/error"); // 404 Not Found
        }
        // Check if the password is correct
        if (!userInDb.getPassword().equals(password)) {
            // Password is incorrect
            return new ModelAndView("redirect:/permissiondenied");// 401 Unauthorized
        }
        // Sign in the user
        return new ModelAndView("redirect:/"); // 200 OK
    }



}
