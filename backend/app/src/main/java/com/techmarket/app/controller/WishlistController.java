package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WishlistController {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/wishlist")
    public String wishlist(Model model, @PageableDefault(size = 10) Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        if (!user.getWishlist().isEmpty()) {
            // Model the cart using the products from the database
            Page<Product> products = productRepository.findProductsInWishlist(user.getEmail(), pageable);
            List<Product> productList = user.getWishlist();
            // Remove the products that are not in the wishlist and are in the cart
            for (Product product : products) {
                if (!productList.contains(product)) {
                    products.getContent().remove(product);
                }
            }
            model.addAttribute("items", productList);
            model.addAttribute("total", products.getTotalElements());
            if (products.getTotalElements() > 10) {
                model.addAttribute("hasMore", true);
            } else {
                model.addAttribute("hasMore", false);
            }
            return "wishlist";
        } else {
            model.addAttribute("items", null);
            model.addAttribute("total", 0);
            model.addAttribute("hasMore", false);
            return "wishlist";
        }

    }

    @GetMapping("/wishlist/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {

        User user = UserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Product> page = productRepository.findProductsInShoppingCart(user.getEmail(), pageable);
        List<Product> productList = user.getWishlist();
        // Check if the elements are on the page and the wishlist
        for (Product product : page) {
            if (!productList.contains(product)) {
                page.getContent().remove(product);
            }
        }



        return JSONService.getProductStringResponseEntity(page);
    }



    @GetMapping("/add-to-wishlist")
    public String addToCart(int productId) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = UserRepository.findByEmail(authentication.getName());
        // Add the product to the wishlist
        user.getWishlist().add(new Product(productId));  // This will add the product to the cart, but it will not be a product from the database, it will be a product with only the productId, so it will not have the product name, price, etc.
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/wishlist";
    }

    @GetMapping("/remove-from-wishlist")
    public String removeFromCart(int productId) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = UserRepository.findByEmail(email);
        // Remove the product from the wishlist
        user.getWishlist().remove(new Product(productId));
        // Save the changes to the database
        UserRepository.save(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/wishlist";
    }

}
