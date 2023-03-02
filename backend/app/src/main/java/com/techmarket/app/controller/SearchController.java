package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/search")
    public String search(Model model, @RequestParam("product") String product) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Product> page = productRepository.findByProductNameContaining(product, pageable);

        if (page.isEmpty()) {
            model.addAttribute("results", null);
        } else {
            model.addAttribute("results", page.getContent());
        }
        model.addAttribute("total", page.getTotalElements()); // Total number of results
        model.addAttribute("product", product); // Search query
        model.addAttribute("hasMore", page.hasNext()); // Boolean to check if there are more results
        return "searchresults";
    }


    @GetMapping("search/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("product") String product,
                                           @RequestParam("start") int start) throws JsonProcessingException {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Product> page = productRepository.findByProductNameContaining(product, pageable);

        // Send the results to the ajax call
        return JSONService.getProductStringResponseEntity(page);
    }


}
