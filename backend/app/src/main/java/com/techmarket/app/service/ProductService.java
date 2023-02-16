package com.techmarket.app.service;

import com.techmarket.app.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {

    public List<Product> getProducts() {
        // Get products from database
        return new ArrayList<Product>();
    }

    public Product getProduct(String productId) {
        // Get product from database
        return new Product();
    }

    public void addProduct(Product product) {
        // Add product to database
    }

    public void updateProduct(Product product) {
        // Update product in database
    }

    public void deleteProduct(String productId) {
        // Delete product from database
    }

    private void deleteAllProducts() {
        // Delete all products from database
        // Useful for deleting all products when the database is reset
    }
}
