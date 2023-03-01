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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Product> page = productRepository.findAll(pageable);

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
        Page<Product> page = productRepository.findAll(pageable);

        return JSONService.getStringResponseEntity(page);
    }

    @GetMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
}
