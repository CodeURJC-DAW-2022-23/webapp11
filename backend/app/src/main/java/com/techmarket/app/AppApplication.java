package com.techmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        // Disable tls 1.0 and 1.1
        System.setProperty("https.protocols", "TLSv1.2");
        SpringApplication.run(AppApplication.class, args);
    }
}
