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


    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("name", product.get().getProductName());
            model.addAttribute("product", product.get());
            return "product";
        } else {
            return "redirect:/error"; // 404 Not Found, product not found, redirected to main error page
        }
    }

    @GetMapping("/pricehistory/{id}")
    public String showPriceHistory(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "pricehistory";
        } else {
            return "/dashboard";
        }
    }

    @GetMapping("/addproduct")
    public String addproduct() {
        return "addproduct";
    }

    @Transactional
    @PostMapping("/addproduct-create")
    public String createproduct(@RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam int amount, @RequestParam List<String> tags, @RequestParam MultipartFile mainImage, @RequestParam(required = false) MultipartFile[] moreImages) throws IOException, SQLException {
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
        product.setProductStock(amount);
        product.setTags(tags);
        product.setMainImage(image);
        // Create new product
        productRepository.save(product);
        return "redirect:/dashboard";

    }

    @GetMapping("/product/{id}/editproduct")
    public String editproduct(@PathVariable long id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("productName", product.getProductName());
        model.addAttribute("productDescription", product.getDescription());
        model.addAttribute("productPrice", product.getProductPrice());
        model.addAttribute("productStock", product.getProductStock());
        model.addAttribute("tags", product.getTags());


        return "editproduct";
    }

    @Transactional
    @PostMapping("/editproduct-update/{id}")
    public String editproductupdate(@PathVariable long id, @RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam int amount, @RequestParam List<String> tags, @RequestParam MultipartFile mainImage, @RequestParam MultipartFile[] moreImages) throws IOException, SQLException {
        System.out.println(mainImage.getSize()>0);
        System.out.println(moreImages.length>0);
        Product product = productRepository.findByProductId(id);
        if (name != null) {
            product.setProductName(name);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (price != 0) {
            product.setProductPrice(price);
        }

        if (amount != 0) {
            product.setProductStock(amount);
        }
        if (tags != null) {
            product.setTags(tags);
        }
        if (mainImage.getSize()>0) {
            //First we delete any existing Main image and then replace it with the new one
            imageRepository.deleteByImageId(product.getMainImage().getImageId());
            product.setMainImage(null);
            Image image = new Image();
            image.setFileName(mainImage.getOriginalFilename());
            image.setImageBlob(new SerialBlob(mainImage.getBytes()));
            imageRepository.save(image);
            product.setMainImage(image);
        }
        if (moreImages.length>0) {
            //First we delete all the other images and replace them with the new ones
            imageRepository.deleteAll(product.getImages());
            product.setImages(null);
            for (MultipartFile file : moreImages) {
                if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                    Image image = new Image();
                    image.setFileName(file.getOriginalFilename());
                    image.setImageBlob(new SerialBlob(file.getBytes()));
                    imageRepository.save(image);
                    product.getImages().add(image);

                } else {
                    return "error";  // 400 Bad Request, user didn't upload an image, redirected to main error page
                }

            }
        }

        productRepository.save(product);
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteByProductId(id);
        return "redirect:/dashboard";
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
            return "addreview";
        } else {
            return "redirect:/products" + id;  // The user has not bought the product
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/product/{ProductId}/send-review")
    public String addReview(@PathVariable("ProductId") Long id, @RequestParam String reviewTitle, @RequestParam String reviewText, @RequestParam int rating, @RequestParam(required = false) MultipartFile[] images) throws IOException, SQLException {
        // Check if product exists
        Optional<Product> product = productService.getProductById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Create review
        Review review = new Review();
        if (images != null) {
            List<Image> imageList = new ArrayList<>();
            review.setImages(imageList);
            for (MultipartFile file : images) {
                if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                    Image image = new Image();
                    image.setFileName(file.getOriginalFilename());
                    image.setImageBlob(new SerialBlob(file.getBytes()));
                    imageList.add(image);
                    imageRepository.save(image);
                } else {
                    return "redirect:/error";  // Error trying to upload an image
                }
            }
        }
        review.setReviewTitle(reviewTitle);
        review.setReviewText(reviewText);
        review.setRating(rating);
        review.setProduct(product.get());
        review.setUser(currentUser);
        reviewRepository.save(review);
        // Add review to user and product
        currentUser.getReviews().add(review);
        product.get().getReviews().add(review);
        userRepository.save(currentUser);
        productRepository.save(product.get());
        return "redirect:/product/" + id;
    }
}
