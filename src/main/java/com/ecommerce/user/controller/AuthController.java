package com.ecommerce.user.controller;

import com.ecommerce.user.dto.*;
import com.ecommerce.user.security.JwtUtil;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        log.info("API /register called for username: {}", req.getUsername());

        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        log.info("API /login called for username: {}", req.getUsername());

        String username = service.login(req);
        String token = jwtUtil.generateToken(username);

        log.info("JWT token generated for username: {}", username);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}