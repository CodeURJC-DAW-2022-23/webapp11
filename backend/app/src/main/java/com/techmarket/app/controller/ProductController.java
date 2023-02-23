package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class    ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")
    public String product() {
        return "product";
    }

    //we still have to make it only show 10 products
    @GetMapping("/products")
    public String products(Model model){
        List<Product> products = productService.getAll();
        model.addAttribute("products",products);
        return "/products";
    }

    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "product";
        }else{
            return "/products"; //not sure of this
        }
    }

    @GetMapping("/addproduct")
    public String addproduct(){
        return "addproduct";
    }

    @Transactional
    @PostMapping("/addproduct-create")
    public ResponseEntity<Product> createproduct(@RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam String discount, @RequestParam int amount, @RequestParam List<String> tags) {
        Product product = new Product(name, description, price, discount, amount, tags);
        // Create new product
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);  // 201 Created, this will also return the user object in the response body
        // If there's information missing and the product can't be created, the response will be 400 Bad Request, Spring will handle that
    }
    
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        //productService.deleteAllByProductId(id);
        return "redirect:/products";
    }
}
