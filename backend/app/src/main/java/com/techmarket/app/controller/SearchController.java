package com.techmarket.app.controller; // This doesn't match the file path for some reason

import com.techmarket.app.model.Product;
import com.techmarket.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam("product") String product,
                         @RequestParam(value = "start", required = false, defaultValue = "0") int start) {
    List<Product> products = productService.searchProducts(product);
    // 5 results per row
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
