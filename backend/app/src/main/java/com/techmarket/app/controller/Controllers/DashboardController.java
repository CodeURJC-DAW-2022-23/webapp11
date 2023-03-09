package com.techmarket.app.controller.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.Review;
import com.techmarket.app.service.JSONService;
import com.techmarket.app.service.ProductService;
import com.techmarket.app.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {






    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;





    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Product> page = productService.getAllProducts(pageable);

        if (page.isEmpty()) {
            model.addAttribute("item", null);
            model.addAttribute("hasMore", false);

        } else {
            model.addAttribute("item", page.getContent());

        }

        model.addAttribute("total", page.getTotalElements());
        model.addAttribute("hasMore", page.hasNext());

        return "dashboard";
    }

    @GetMapping("/dashboard/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Product> page = productService.getAllProducts(pageable);

        return JSONService.getProductStringResponseEntity(page);
    }

    @GetMapping("/reviewhistory/{id}")
    public String reviewHistory(Model model, @PathVariable("id") Long id, @PageableDefault(size = 10) Pageable pageable) {

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        Page<Review> page = reviewService.getReviewsByProduct(product, pageable);
        if (page.isEmpty()) {
            model.addAttribute("items", null);
            model.addAttribute("hasMore", false);
            model.addAttribute("total", 0);
        } else {
            model.addAttribute("items", page.getContent());
        }

        model.addAttribute("total", page.getTotalElements());
        model.addAttribute("hasMore", page.hasNext());

        return "reviewhistory";
    }

    @GetMapping("/reviewhistory/{id}/loadmore")
    public ResponseEntity<String> loadMoreReviewHistory(@PathVariable("id") Long id, @RequestParam("start") int start) throws JsonProcessingException {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Product product = productService.getProductById(id);
        Page<Review> page = reviewService.getReviewsByProduct(product, pageable);

        return JSONService.getReviewStringResponseEntity(page);
    }

    @GetMapping("/removereview/{id}")
    public String removeReview(@PathVariable("id") long id) {
        // We have to delete the review from the user's review list
        Review review = reviewService.getReviewById(id);
        reviewService.deleteReviewById(id);
        return "redirect:/dashboard";
    }
}
