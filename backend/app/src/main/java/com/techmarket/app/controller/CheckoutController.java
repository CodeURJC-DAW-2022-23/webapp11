package com.techmarket.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class CheckoutController {
    @RequestMapping("/checkout") // Map ONLY GET Requests
    // Returns the checkout page
    public String checkout() {
        return "checkout";
    }

    @RequestMapping("/checkout/complete") // Map ONLY GET Requests
    // Returns the confirmation page
    public String checkoutComplete() {
        return "checkoutcomplete";
    }
}
