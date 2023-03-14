package com.techmarket.app.controller.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

import static com.techmarket.app.service.JSONService.getStringResponseEntity;

@Controller
public class CartController {


    @Autowired
    private UserService userService;

    @Autowired ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model, @PageableDefault() Pageable pageable) {
        // Access the user's cart using the session using the SecurityContext and user service with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserName(authentication.getName());

        // Check if the cart is empty
        if (!user.getShoppingCart().isEmpty()) {
            List<Product> productList = user.getShoppingCart();
            model.addAttribute("total", productList.size());
            model.addAttribute("hasItems", true);
            if (productList.size() > 10) {
                model.addAttribute("hasMore", true);
            } else {
                model.addAttribute("hasMore", false);
            }
            // Calculate the total price of the cart
            double totalPrice = 0;
            for (Product product : productList) {
                totalPrice += product.getProductPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
            // Limit the number of products to 10
            productList = productList.subList(0, Math.min(productList.size(), 10));
            model.addAttribute("items", productList);

        } else {
            model.addAttribute("items", null);
            model.addAttribute("total", 0);
            model.addAttribute("hasItems", false);
            model.addAttribute("hasMore", false);
            model.addAttribute("totalPrice", 0);
        }
        return "cart";
    }

    @GetMapping("/cart/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {

        User user = userService.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Product> productList = user.getShoppingCart();
        return getStringResponseEntity(start, productList);
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {
        // Access the user's cart using the session using the SecurityContext and user service with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserName(authentication.getName());
        // Add the product to the cart (the product is identified by its id)
        user.getShoppingCart().add(productService.getProductById(id));
        // Save the changes to the database
        userService.saveUser(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }

    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id) {
        // Access the user's cart using the session using the SecurityContext and user service with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserName(authentication.getName());
        // Remove the product from the cart (the product is identified by its id)
        user.getShoppingCart().removeIf(product -> Objects.equals(product.getProductId(), id));
        // Save the changes to the database
        userService.saveUser(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/cart";
    }
}
