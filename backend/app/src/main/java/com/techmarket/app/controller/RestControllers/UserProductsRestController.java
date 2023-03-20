package com.techmarket.app.controller.RestControllers;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.RecommendationService;
import com.techmarket.app.service.UserProductsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.techmarket.app.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProductsRestController{


    @Autowired
    private UserService userService;

    @Autowired
    private UserProductsService userProductsService;
    @Autowired
    private ProductService productService;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping(value="/cart" ,params = {"page", "size"})
    public ResponseEntity<Page<Product>> getCartProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> cartProducts = userProductsService.getCartProducts(pageable,user);

        return ResponseEntity.ok(cartProducts);

    }

    @GetMapping(value="/wishlist" ,params = {"page", "size"})
    public ResponseEntity<Page<Product>> getWishlistProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                             HttpServletRequest request) {

        User user = userService.getCurrentUser(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> wishlistProducts = userProductsService.getWishlistProducts(pageable,user);
        return ResponseEntity.ok(wishlistProducts);
    }


    @PostMapping(value="/cart/addProduct/{id}")
    public ResponseEntity<AuthResponse> addProductToCart(@PathVariable Long id,HttpServletRequest request) {
       User user = userService.getCurrentUser(request);
       user.getShoppingCart().add(productService.getProductById(id));
       userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product added to cart");
        return ResponseEntity.ok(authResponse);

    }

   @DeleteMapping(value="/cart/removeProduct/{id}")
    public ResponseEntity<AuthResponse> removeProductFromCart(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getShoppingCart().remove(productService.getProductById(id));
       userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product removed from cart");
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value="/wishlist/addProduct/{id}")
    public ResponseEntity<AuthResponse> addProductToWishlist(@PathVariable Long id, HttpServletRequest request) {
        User user  = userService.getCurrentUser(request);
        user.getWishlist().add(productService.getProductById(id));
        userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product added to wishlist");
        return ResponseEntity.ok(authResponse);

    }

    @DeleteMapping(value="/wishlist/removeProduct/{id}")
    public ResponseEntity<AuthResponse> removeProductFromWishlist(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getWishlist().remove(productService.getProductById(id));
        userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product removed from wishlist");
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Product>> getRecommendations() {
        List<Product> recommendations = recommendationService.getRecommendedProducts();
        return ResponseEntity.ok(recommendations);
    }







}
