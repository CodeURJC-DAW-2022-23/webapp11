package com.techmarket.app.controller.RestControllers;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
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

@RestController
@RequestMapping("/api")
public class UserProductsRestController{


    @Autowired
    private UserService userService;

    @Autowired
    private UserProductsService userProductsService;
    @Autowired
    private ProductService productService;

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


    @PostMapping(value="/cart/addProduct")
    public ResponseEntity<Void> addProductToCart(@RequestBody Product product, HttpServletResponse response, HttpServletRequest request) {
        Product newProduct = productService.createProduct(product);
        User user = userService.getCurrentUser(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")  // get the current request and add the id to the end
                .buildAndExpand(newProduct.getProductId()).toUri();  // get the id of the new product and expand it to the uri, so it can be used
        response.setHeader("Location", location.toString());
        user.getShoppingCart().add(newProduct);
        return ResponseEntity.created(location).build();

    }

   @DeleteMapping(value="/cart/removeProduct/{id}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getShoppingCart().remove(productService.getProductById(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/wishlist/addProduct")
    public ResponseEntity<Void> addProductToWishlist(@RequestBody Product product, HttpServletResponse response, HttpServletRequest request) {
        Product newProduct = productService.createProduct(product);
        User user = userService.getCurrentUser(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")  // get the current request and add the id to the end
                .buildAndExpand(newProduct.getProductId()).toUri();  // get the id of the new product and expand it to the uri, so it can be used
        response.setHeader("Location", location.toString());
        user.getWishlist().add(newProduct);
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping(value="/wishlist/removeProduct/{id}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        user.getWishlist().remove(productService.getProductById(id));
        return ResponseEntity.noContent().build();
    }







}
