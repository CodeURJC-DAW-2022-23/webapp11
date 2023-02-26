package com.techmarket.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {
    @GetMapping("/error") // Map ONLY GET Requests
    // Returns the error page
    public String error() {
        return "error";
    }

    @GetMapping("/404") // Map ONLY GET Requests
    // Returns the 404 page
    public String notFound() {
        return "error";
    }

    @GetMapping("/permissiondenied")
    public String permissiondenied() {
        return "access-denied";
    }
}
