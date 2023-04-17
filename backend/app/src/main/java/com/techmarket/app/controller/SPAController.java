package com.techmarket.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {
    // Forward request made to /new and /new/** to /new/index.html

    @GetMapping({"/new", "/new/**"})
    public String spa() {
        return "forward:/new/index.html";
    }
}