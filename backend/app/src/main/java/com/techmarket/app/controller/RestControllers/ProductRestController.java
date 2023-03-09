package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Product;
import com.techmarket.app.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/{product}")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @PathVariable String product) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(pageable, product);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product, HttpServletResponse response) {
        Product newProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")  // get the current request and add the id to the end
                .buildAndExpand(newProduct.getProductId()).toUri();  // get the id of the new product and expand it to the uri, so it can be used
        response.setHeader("Location", location.toString());  // set the location of the new product
        return ResponseEntity.created(location).build();  // return the created status and the location of the new product
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteByProductId(id);
        return ResponseEntity.noContent().build();  // return the no content status
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }


}
