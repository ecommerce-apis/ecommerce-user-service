package com.ecommerce.user;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import java.util.TimeZone;
@SpringBootApplication
@EnableKafka
public class UserServiceApplication {
//    @PostConstruct
//    public void init() {
//        // This forces the Java App to use Kolkata, matching your Postgres container
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
//    }
    static {
        // This MUST happen before any Spring or Hibernate classes load
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
    public static void main(String[] args) {
        io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.load();

        // Set environment variables for Spring Boot
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASS", dotenv.get("DB_PASS"));

        SpringApplication.run(UserServiceApplication.class, args);
    }
}