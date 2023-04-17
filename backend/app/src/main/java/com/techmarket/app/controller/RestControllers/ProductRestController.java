package com.techmarket.app.controller.RestControllers;

import com.techmarket.app.model.Image;
import com.techmarket.app.model.Product;
import com.techmarket.app.service.ImageService;
import com.techmarket.app.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

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

    @GetMapping("/search/")
    @Operation(summary = "Search products by name")
    @ApiResponse(responseCode = "200", description = "Products retrieved")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam String product) {
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
    public ResponseEntity<Product> createProduct(@RequestParam("productName") String productName, @RequestParam("description")  String description,@RequestParam("price") String price, @RequestParam("amount") String amount, @RequestParam("tags") String tags, @RequestParam("image") MultipartFile image,@RequestParam("images") MultipartFile[] images) throws IOException, SQLException {
        Product newProduct = new Product();
        newProduct.setProductPrices(new ArrayList<>());
        newProduct.setImages(new ArrayList<>());
        newProduct.setProductName(productName);
        newProduct.setDescription(description);
        newProduct.setProductPrice(Double.parseDouble(price));
        newProduct.setProductStock(Integer.parseInt(amount));
        newProduct.setTags(Arrays.asList(tags));
        newProduct.setReviews(new ArrayList<>());
        newProduct.setReviews(new ArrayList<>());
        Image mainImage = new Image();
        mainImage.setFileName(image.getOriginalFilename());
        mainImage.setImageBlob(new SerialBlob(image.getBytes()));
        imageService.saveImage(mainImage);
        newProduct.setMainImage(mainImage);
        //do the same with the images array
        for (MultipartFile img : images) {
            Image newImage = new Image();
            newImage.setFileName(img.getOriginalFilename());
            newImage.setImageBlob(new SerialBlob(img.getBytes()));
            imageService.saveImage(newImage);
            newProduct.getImages().add(newImage);
        }

        productService.saveProduct(newProduct);




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

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update a product by id")
    @ApiResponse(responseCode = "200", description = "Product updated")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam("productName") String productName, @RequestParam("description")  String description,@RequestParam("productPrice") String productPrice, @RequestParam("productStock") String productstock, @RequestParam("tags") String tags, @RequestParam("mainImage") MultipartFile mainImage,@RequestParam("images") MultipartFile[] images) throws Exception {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/price-history/{id}")
    @Operation(summary = "Get a product's price history")
    @ApiResponse(responseCode = "200", description = "Product's price history retrieved")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<List<Double>> getProductPriceHistory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id).getProductPrices());
    }

    @GetMapping("remove-from-stock/{id}")
    @Operation(summary = "Remove a product from stock")
    @ApiResponse(responseCode = "200", description = "Product removed from stock")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Product> removeFromStock(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setProductStock(0);

        productService.saveProduct(product);
        return ResponseEntity.ok(product);
    }

}
