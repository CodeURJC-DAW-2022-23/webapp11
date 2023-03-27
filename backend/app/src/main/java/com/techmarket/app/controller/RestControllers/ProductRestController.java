package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Product;
import com.techmarket.app.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping(params = {"page", "size"})
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "Get a product by id")
    @ApiResponse(responseCode = "200", description = "Product retrieved")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/{product}")
    @Operation(summary = "Search products by name")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @PathVariable String product) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(pageable, product);
        return ResponseEntity.ok(products);
    }

    // Create a new product
    @PostMapping("/add-product")
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created")
    @ApiResponse(responseCode = "400", description = "Product not created")
    @ApiResponse(responseCode = "403", description = "User not authorized")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        // Return the location of the new product
        return ResponseEntity.created(URI.create("/api/products/" + newProduct.getId())).body(newProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by id")
    @ApiResponse(responseCode = "204", description = "Product deleted")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteByProductId(id);
        return ResponseEntity.noContent().build();  // return the no content status
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product by id")
    @ApiResponse(responseCode = "200", description = "Product updated")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/price-history/{id}")
    @Operation(summary = "Get a product's price history")
    @ApiResponse(responseCode = "200", description = "Product's price history retrieved")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<?> getProductPriceHistory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id).getProductPrices());
    }

}
