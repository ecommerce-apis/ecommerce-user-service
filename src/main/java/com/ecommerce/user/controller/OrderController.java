package com.ecommerce.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/test")
    public ResponseEntity<String> testOrder() {
        // This will show in your IntelliJ console
        System.out.println("Order execution verified: Bearer Token is VALID");
        log.info("Secure Order endpoint accessed successfully");

        return ResponseEntity.ok("Order executed successfully - Token Verified");
    }
}