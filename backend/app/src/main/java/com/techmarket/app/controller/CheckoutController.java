package com.techmarket.app.controller;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;


@Controller
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        User user = userRepository.findByEmail(authentication.getName());
        List<Product> cart = user.getShoppingCart();
        model.addAttribute("cart", cart);
        double price = 0;
        for (Product product : cart) {
            price+=product.getProductPrice();
        }
        model.addAttribute("totalPrice", price);

        return "checkout";

    }

}