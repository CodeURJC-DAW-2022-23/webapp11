package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.annotation.SessionScope;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/product")
    public String product() {
        return "product";
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Product> createproduct(@RequestParam String name, @RequestParam String description, @RequestParam String price, @RequestParam String discount, @RequestParam int amount, @RequestParam List<String> tags) {
        Product product = new Product(name, description, price, discount, amount, tags);
        if (productRepository.findByProductName(name) != null) {
            // Product already exists
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict, we can return something meaningful and not a blank screen
        }
        // Create new product
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED); // 201 Created, this will also return the user object in the response body
        // If there's information missing and the product can't be created, the response will be 400 Bad Request, Spring will handle that
    }
}
