package com.techmarket.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "3TechMarket API", version = "1.0", description = "3TechMarket API, fancyyyyyy"))
public class AppApplication {

    public static void main(String[] args) {
        // Disable tls 1.0 and 1.1
        System.setProperty("https.protocols", "TLSv1.2");
        SpringApplication.run(AppApplication.class, args);
    }
}
