package com.techmarket.app.controller;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/search")
    public String search(Model model, @RequestParam("product") String product,
                         @RequestParam(name = "start", defaultValue = "0") int start, Principal principal) {
    if (principal != null) {
        model.addAttribute("isLoggedIn", true);
    } else {
        model.addAttribute("isLoggedIn", false);
    }

    List<Product> products = productRepository.findByProductNameContaining(product);  // Get all products that match the search query
    // 5 results per row, 10 results per page
    int end = Math.min(start + 10, products.size()); // End index for current page (10 results per page)
    List<Product> results = products.subList(start, end); // Get results for current page, if the user clicks next page, start will be 10 and end will be 20
    if (results.isEmpty()) {
      model.addAttribute("results", null);
    } else {
      model.addAttribute("results", results);
    }
    model.addAttribute("total", products.size());  // Total number of results
    model.addAttribute("product", product);  // Search query
    model.addAttribute("start", end);  // Start index for next page
    model.addAttribute("hasMore", end < products.size());  // Boolean to check if there are more results
    return "searchresults";
  }

  @GetMapping("search/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("product") String product,
                                           @RequestParam("start") int start) {
    List<Product> products = productRepository.findByProductNameContaining(product);
    int end = Math.min(start + 10, products.size());
    List<Product> results = products.subList(start, end);
    StringBuilder sb = new StringBuilder();
    // This is hideous, but it works, and Mustache was giving errors trying to return partial views
    for (Product p : results) {
      sb.append("<div class=\"col-md-2\">");
      sb.append("<a href=\"/product/"+ p.getProductId() +"\">");
      sb.append("<img src=\"/product/"+ p.getProductId() +"/image\" alt=\"resultimage\" width=\"120\" height=\"120\">");
      sb.append("<p class=\"name\">"+ p.getProductName() +"</p>");
      sb.append("<p>"+ p.getProductPrice() +" € <span class=\"badge text-bg-success\">"+ p.getProductStock() +"</span></p>");
      sb.append("</a>");
      sb.append("</div>");
  }
    return ResponseEntity.ok(sb.toString());
    }

}
