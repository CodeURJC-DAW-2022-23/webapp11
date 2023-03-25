package com.techmarket.app.controller.RestControllers;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.security.jwt.AuthResponse;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.RecommendationService;
import com.techmarket.app.service.UserProductsService;
import com.techmarket.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Get all products in cart")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<Page<Product>> getCartProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> cartProducts = userProductsService.getCartProducts(pageable,user);

        return ResponseEntity.ok(cartProducts);

    }

    @GetMapping(value="/wishlist" ,params = {"page", "size"})
    @Operation(summary = "Get all products in wishlist")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<Page<Product>> getWishlistProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                             HttpServletRequest request) {

        User user = userService.getCurrentUser(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> wishlistProducts = userProductsService.getWishlistProducts(pageable,user);
        return ResponseEntity.ok(wishlistProducts);
    }


    @PostMapping(value="/cart/addProduct/{id}")
    @Operation(summary = "Add a product to the current user's cart")
    @ApiResponse(responseCode = "200", description = "Product added to cart")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<AuthResponse> addProductToCart(@PathVariable Long id,HttpServletRequest request) {
       User user = userService.getCurrentUser(request);
       user.getShoppingCart().add(productService.getProductById(id));
       userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product added to cart");
        return ResponseEntity.ok(authResponse);

    }

    @DeleteMapping(value="/cart/removeProduct/{id}")
    @Operation(summary = "Remove a product from the current user's cart")
    @ApiResponse(responseCode = "200", description = "Product removed from cart")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<AuthResponse> removeProductFromCart(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getShoppingCart().remove(productService.getProductById(id));
       userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product removed from cart");
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value="/wishlist/addProduct/{id}")
    @Operation(summary = "Add a product to the current user's wishlist")
    @ApiResponse(responseCode = "200", description = "Product added to wishlist")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<AuthResponse> addProductToWishlist(@PathVariable Long id, HttpServletRequest request) {
        User user  = userService.getCurrentUser(request);
        user.getWishlist().add(productService.getProductById(id));
        userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product added to wishlist");
        return ResponseEntity.ok(authResponse);

    }

    @DeleteMapping(value="/wishlist/removeProduct/{id}")
    @Operation(summary = "Remove a product from the current user's wishlist")
    @ApiResponse(responseCode = "200", description = "Product removed from wishlist")
    @ApiResponse(responseCode = "400", description = "Product not found")
    public ResponseEntity<AuthResponse> removeProductFromWishlist(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getWishlist().remove(productService.getProductById(id));
        userService.saveUser(user);
        AuthResponse authResponse = new AuthResponse(AuthResponse.Status.SUCCESS, "Product removed from wishlist");
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get the current user's recommendations")
    @ApiResponse(responseCode = "200", description = "User recommendations retrieved")
    public ResponseEntity<List<Product>> getRecommendations() {
        List<Product> recommendations = recommendationService.getRecommendedProducts();
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/purchase-history")
    @Operation(summary = "Get the current user's purchase history")
    @ApiResponse(responseCode = "200", description = "User purchase history retrieved")
    public ResponseEntity<Page<Purchase>> getPurchaseHistory(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Get the current user
        User user = userService.getCurrentUser(request);

        // Get the user's purchase history
        Page<Purchase> purchaseHistory = userService.getPurchaseHistory(user, page);

        // Return the response
        return ResponseEntity.ok(purchaseHistory);
    }






}
