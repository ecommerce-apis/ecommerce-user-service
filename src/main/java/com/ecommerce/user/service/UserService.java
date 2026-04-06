package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginRequest;
import com.ecommerce.user.dto.RegisterRequest;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.kafka.UserEventProducer;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final UserEventProducer userEventProducer;

    public String register(RegisterRequest req) {

        log.info("Register request received for username: {}", req.getUsername());

        User user = new User();
        user.setUsername(req.getUsername());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setPassword(encoder.encode(req.getPassword()));

        repo.save(user);

        log.info("User saved successfully with username: {}", user.getUsername());

        // Kafka event
        userEventProducer.sendUserCreatedEvent(user.getUsername());

        log.info("Kafka event sent for user-created: {}", user.getUsername());

        return "Register successful";
    }

    public String login(LoginRequest req) {

        log.info("Login attempt for username: {}", req.getUsername());

        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", req.getUsername());
                    return new RuntimeException("User not found");
                });

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            log.error("Invalid password for username: {}", req.getUsername());
            throw new RuntimeException("Invalid credentials");
        }

        log.info("Login successful for username: {}", user.getUsername());

        return user.getUsername();
    }
}