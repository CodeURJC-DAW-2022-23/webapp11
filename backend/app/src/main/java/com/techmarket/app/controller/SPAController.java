package com.techmarket.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {
    // Map the /new and /new/** paths to the SPA index.html
    @GetMapping({"/new", "/new/**"})
    public String index() {
        return "forward:/new/index.html";
    }
}