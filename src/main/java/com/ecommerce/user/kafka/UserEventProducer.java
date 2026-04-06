package com.ecommerce.user.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserCreatedEvent(String username) {
        log.info("Sending Kafka event for user: {}", username);
        kafkaTemplate.send("user-created", username);
    }
}