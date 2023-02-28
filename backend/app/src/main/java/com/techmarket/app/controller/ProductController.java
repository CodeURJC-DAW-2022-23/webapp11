package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ImageRepository;
import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.ReviewRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Image;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.model.User;
import com.techmarket.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/product")
    public String product() {
        return "product";
    }

    //we still have to make it only show 10 products
    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        return "/products";
    }

    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "product";
        } else {
            return "/products"; //not sure of this
        }
    }

    @GetMapping("/addproduct")
    public String addproduct() {
        return "addproduct";
    }

    @Transactional
    @PostMapping("/addproduct-create")
    public String createproduct(@RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam String discount, @RequestParam int amount, @RequestParam List<String> tags, @RequestParam MultipartFile mainImage, @RequestParam(required = false) MultipartFile[] moreImages) throws IOException, SQLException {
        Product product = new Product();
        // Create the list of images
        List<Image> images = new ArrayList<>();
        product.setImages(images);
        for (MultipartFile file : moreImages) {
            if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImageBlob(new SerialBlob(file.getBytes()));
                images.add(image);
                imageRepository.save(image);
            } else {
                return "redirect:/error";  // 400 Bad Request, user didn't upload an image, redirected to main error page
            }
        }
        Image image = new Image();
        image.setFileName(mainImage.getOriginalFilename());
        image.setImageBlob(new SerialBlob(mainImage.getBytes()));
        imageRepository.save(image);
        product.setProductName(name);
        product.setDescription(description);
        List<Double> prices = new ArrayList<>();
        prices.add(price);
        product.setProductPrices(prices);
        product.setProductPrice(price);
        product.setDiscount(discount);
        product.setProductStock(amount);
        product.setTags(tags);
        product.setMainImage(image);
        // Create new product
        productRepository.save(product);
        return "redirect:/dashboard";

    }

    @GetMapping("/editproduct")
    public String editproduct() {
        return "editproduct";
    }

    @Transactional
    @PostMapping("/editproduct-update")
    public ResponseEntity<Product> editproduct(@RequestParam Long id, @RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam String discount, @RequestParam int amount, @RequestParam List<String> tags, @RequestParam MultipartFile mainImage, @RequestParam MultipartFile[] moreImages) throws IOException, SQLException {
        Product product = new Product();
        // Create the list of images
        List<Image> images = new ArrayList<>();
        product.setImages(images);
        for (MultipartFile file : moreImages) {
            if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImageBlob(new SerialBlob(file.getBytes()));
                images.add(image);
                imageRepository.save(image);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // 400 Bad Request, user didn't upload an image
            }
        }
        Image image = new Image();
        image.setFileName(mainImage.getOriginalFilename());
        image.setImageBlob(new SerialBlob(mainImage.getBytes()));
        imageRepository.save(image);
        product.setProductName(name);
        product.setDescription(description);
        List<Double> prices = new ArrayList<>();
        prices.add(price);
        product.setProductPrices(prices);
        product.setProductPrice(price);
        product.setDiscount(discount);
        product.setProductStock(amount);
        product.setTags(tags);
        product.setMainImage(image);
        // Create new product
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);  // 201 Created, this will also return the user object in the response body
        // If there's information missing and the product can't be created, the response will be 400 Bad Request, Spring will handle that
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        //productService.deleteAllByProductId(id);
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/product/{id}/review")
    public String reviewProduct(@PathVariable("id") Long id, Model model) {
        // Check if product exists
        Optional<Product> product = productService.getProductById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Check if the logged-in user has bought the product and not reviewed it yet, as well as if the product still exists
        if (product.isPresent() && currentUser.getPurchasedProducts().contains(product.get()) && !currentUser.getReviews().contains(product.get())) {
            model.addAttribute("product", product.get());
            return "review";
        } else {
            return "redirect:/products" + id;  // The user has not bought the product
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/product/{ProductId}/send-review")
    public String addReview(@PathVariable("ProductId") Long id, @RequestParam String reviewTitle, @RequestParam String reviewText, @RequestParam int rating, @RequestParam(required = false) MultipartFile[] images) {
        // Check if product exists
        Optional<Product> product = productService.getProductById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Check if the logged-in user has bought the product and not reviewed it yet, as well as if the product still exists
        if (product.isPresent() && currentUser.getPurchasedProducts().contains(product.get()) && !currentUser.getReviews().contains(product.get())) {
            // Create review
            Review review = new Review();
            if (images != null) {
                List<Image> imageList = new ArrayList<>();
                for (MultipartFile image : images) {
                    Image img = new Image();
                    img.setImageBlob((Blob) image);
                    imageList.add(img);
                }
                review.setReviewTitle(reviewTitle);
                review.setReviewText(reviewText);
                review.setRating(rating);
                review.setImages(imageList);
            } else {
                review = new Review(reviewTitle, reviewText, rating);
            }
            // Save review
            reviewRepository.save(review);
            // Save product
            productRepository.save(product.get());
            // Save user
            userRepository.save(currentUser);  // This will also save the review on the user table because of the @OneToMany relationship
            return "redirect:/product/" + id;
        } else {
            return "redirect:/products";  // The user has not bought the product
        }
    }
}
