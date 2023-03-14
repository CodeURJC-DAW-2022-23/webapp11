package com.techmarket.app.controller.Controllers;


import com.techmarket.app.model.Image;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class PrivateController {





    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;



    @PreAuthorize("hasAnyAuthority('USER','AGENT', 'ADMIN')")
    @GetMapping("/profile")
    public void profile(Model model) {
        //We want to fill out the form with the user's information when we load the page
        //First we get the user's email with the auth method
        //Then we use the email to get the user's information from the database
        //Then we fill out the form with the user's information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
        // Get user information
        // Get his profile picture from the database, if it exists
        if (currentUser.getProfilePicture() != null) {
            model.addAttribute("hasPfp", true);
            // Get the pfp from the controller
            Long userId = currentUser.getId();
            String pfp = "/" + userId + "/userpfp";
            model.addAttribute("pfp", pfp);
        } else {
            // Default profile picture until the user uploads one
            model.addAttribute("hasPfp", false);
        }
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
    }

    @PreAuthorize("hasAnyAuthority('USER','AGENT', 'ADMIN')")
    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String address, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String area, @RequestParam(required = false) String country, @RequestParam(required = false) String postcode) {
        // Basic edit profile functionality
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
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
        userService.saveUser(currentUser);  // Save the changes to the database
        return "redirect:/profile";
        }

    @PostMapping("/edit-pfp")
    public String editPfp(@RequestParam(required = false) MultipartFile pfp) throws IOException, SQLException {
        if (pfp == null) {
            return "redirect:/profile";
        }
        if (pfp.isEmpty()) {
            return "redirect:/profile";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
        // Check if the file is empty
        // We already checked the user can only upload jpg or png files, so we don't need to check the file type
        Image image = new Image();
        image.setFileName(pfp.getOriginalFilename());
        image.setImageBlob(new SerialBlob(pfp.getBytes()));
        currentUser.setProfilePicture(image);
        imageService.saveImage(image);
        userService.saveUser(currentUser);  // Save the changes to the database
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
