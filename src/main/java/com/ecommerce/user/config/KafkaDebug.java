package com.ecommerce.user.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaDebug {

    private final Environment env;

    @PostConstruct
    public void checkKafka() {
        String kafkaServer = env.getProperty("spring.kafka.bootstrap-servers");
        System.out.println(" Kafka server from Spring = " + kafkaServer);
    }
}