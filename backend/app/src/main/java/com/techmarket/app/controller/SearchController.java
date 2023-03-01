package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/search")
    public String search(Model model, @RequestParam("product") String product,
                         @RequestParam(name = "start", defaultValue = "0") int start) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Product> page = productRepository.findByProductNameContaining(product, pageable);

        List<Product> results = page.getContent(); // Get results for current page
        if (results.isEmpty()) {
            model.addAttribute("results", null);
        } else {
            model.addAttribute("results", results);
        }
        model.addAttribute("total", page.getTotalElements()); // Total number of results
        model.addAttribute("product", product); // Search query
        model.addAttribute("start", start + results.size()); // Start index for next page
        model.addAttribute("hasMore", page.hasNext()); // Boolean to check if there are more results
        return "searchresults";
    }


    @GetMapping("search/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("product") String product,
                                           @RequestParam("start") int start) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Product> page = productRepository.findByProductNameContaining(product, pageable);

        List<Product> results = page.getContent(); // Get results for current page

        // Send the results to the ajax call
        Map<String, Object> map = new HashMap<>();
        map.put("items", results);

        // Convert the map to JSON
        // We used StringBuilder to send the ResponseBody built with StringBuilder, but now we use Jackson (ObjectMapper)
        // So the process of building the HTML is done in the frontend by JavaScript
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }


}
