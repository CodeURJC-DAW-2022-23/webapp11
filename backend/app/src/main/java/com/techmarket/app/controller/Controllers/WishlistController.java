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
public class WishlistController {





    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;



    @GetMapping("/wishlist")
    public String wishlist(Model model, @PageableDefault(size = 10) Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserName(authentication.getName());
        if (!user.getWishlist().isEmpty()) {
            List<Product> productList = user.getWishlist();
            model.addAttribute("total", productList.size());
            if (productList.size() > 10) {
                model.addAttribute("hasMore", true);
            } else {
                model.addAttribute("hasMore", false);
            }
            productList = productList.subList(0, Math.min(productList.size(), 10));
            model.addAttribute("items", productList);
        } else {
            model.addAttribute("items", null);
            model.addAttribute("total", 0);
            model.addAttribute("hasMore", false);
        }
        return "wishlist";
    }

    @GetMapping("/wishlist/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {

        User user = userService.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Product> productList = user.getWishlist();
        return getStringResponseEntity(start, productList);
    }


    @GetMapping("/add-to-wishlist/{id}")
    public String addToWishlist(@PathVariable Long id) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserName(authentication.getName());
        // Add the product to the wishlist
        user.getWishlist().add(productService.getProductById(id));
        // Save the changes to the database
        userService.saveUser(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/wishlist";
    }

    @GetMapping("/remove-from-wishlist/{id}")
    public String removeFromWishlist(@PathVariable Long id) {
        // Access the user's wishlist using the session using the SecurityContext and user repository with the email
        User user = userService.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        // Remove the product from the wishlist
        user.getWishlist().removeIf(product -> Objects.equals(product.getProductId(), id));  // This will remove the product from the wishlist if the product id is equal to the id passed in the path
        // Save the changes to the database
        userService.saveUser(user);  // This will update the user's cart as the cart is a list of products on the user model
        return "redirect:/wishlist";
    }

}
