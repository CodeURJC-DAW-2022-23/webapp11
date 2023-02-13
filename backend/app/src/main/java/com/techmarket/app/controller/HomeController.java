package com.techmarket.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class HomeController {
    @RequestMapping("/") // Map ONLY GET Requests
    // Returns the index page
    public String index() {
        return "index";
    }
}
