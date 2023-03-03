package com.techmarket.app.controller;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import com.techmarket.app.security.EncoderConfiguration;
import com.techmarket.app.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.Objects;


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
    public String signup(Model model, HttpServletRequest request) {
        String error = request.getParameter("error");
        if (error != null && error.equals("409")) {
            model.addAttribute("error409", true);
        }
        if (error != null && error.equals("400")) {
            model.addAttribute("success400", true);
        }
        return "register";
    }

    // Sign in page
    @GetMapping("/signin")
    public String signin(Model model, HttpServletRequest request) {
        if (request.getParameter("error") != null) {
            model.addAttribute("error", true);
        }
        if (request.getParameter("success") != null) {
            model.addAttribute("success", true);
        }
        return "login";
    }

    @GetMapping("/recovery")
    public String recovery(Model model, HttpServletRequest request){
        String error = request.getParameter("error");
        if (error != null && error.equals("400")) {
            model.addAttribute("error400", true);
        }
        return "recovery";
    }
    @GetMapping("/code")
    public String code(Model model, HttpServletRequest request){
        String error = request.getParameter("error");
        if (error != null && error.equals("400")) {
            model.addAttribute("error400", true);
        }
        return "code";
    }

    @GetMapping("/changepassword")
    public String changepassword(){
        return "changepassword";
    }

    // Sign up a new user
    @PostMapping("/signup-user")
    public String signupuser(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName) throws MessagingException {
        User user = new User( email,  firstName, lastName);
        user.setEncodedPassword(passwordEncoder.passwordEncoder().encode(password));
        // Set a default profile picture
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
    @PostMapping("/recover-email")
    public String recover(@RequestParam String email, Model model) throws MessagingException {
        User currentUser = userRepository.findByEmail(email);
        if (currentUser != null) {
            // User created, 201 Created
            emailService.sendAccountRecoveryEmail(email, currentUser.getFirstName(), currentUser.getToken());
            return "redirect:/code?email=" + email;
        } else {
            // User not created, 400 Bad Request
            return "redirect:/recovery?error=400";
        }
        // If there's information missing and the user can't be created, the response will be 400 Bad Request, Spring will handle that

    }
    @PostMapping("/verify-code")
    public String passwordChanger(@RequestParam String email, @RequestParam Long code, @RequestParam String password) throws MessagingException {
        User currentUser = userRepository.findByEmail(email);
        if (Objects.equals(code, currentUser.getToken())){
            currentUser.setEncodedPassword(passwordEncoder.passwordEncoder().encode(password));
            userRepository.save(currentUser);
            return "redirect:/signin?success=201";  // Redirect to sign in page
        }else {
            return "redirect:/code?error=400";
        }
        // If there's information missing and the user can't be created, the response will be 400 Bad Request, Spring will handle that

    }

    // Sign in an existing user (this is handled by Spring)


}
