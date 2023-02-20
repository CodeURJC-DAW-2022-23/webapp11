package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    // Field injection is not recommended because it makes the code harder to test
    private final ProductRepository productRepository;

    public SearchController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam("product") String product,
                         @RequestParam(value = "start", required = false, defaultValue = "0") int start) {
    List<Product> products = productRepository.findByProductName(product);
    // 5 results per row, 10 results per page
    int end = Math.min(start + 10, products.size()); // End index for current page (10 results per page)
    List<Product> results = products.subList(start, end); // Get results for current page, if the user clicks next page, start will be 10 and end will be 20
    if (results.isEmpty()) {
      model.addAttribute("results", null);
    } else {
      model.addAttribute("results", results); // Mustache should iterate over this list and print 5 results per row
    }
    model.addAttribute("product", product);  // Search query
    model.addAttribute("start", end);  // Start index for next page
    model.addAttribute("hasMore", end < products.size());  // Boolean to check if there are more results
    return "searchtemplate";  // Return searchtemplate.html
  }
}
