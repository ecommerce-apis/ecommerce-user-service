//package com.ecommerce.user.service;
//
//import com.ecommerce.user.dto.LoginRequest;
//import com.ecommerce.user.dto.RegisterRequest;
//import com.ecommerce.user.entity.User;
//import com.ecommerce.user.kafka.UserEventProducer;
//import com.ecommerce.user.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository repo;
//
//    @Mock
//    private PasswordEncoder encoder;
//
//    @Mock
//    private UserEventProducer producer;
//
//    @InjectMocks
//    private UserService service;
//
//    @Test
//    void register_shouldSaveUser_andSendKafkaEvent() {
//
//        RegisterRequest req = new RegisterRequest();
//        req.setUsername("john");
//        req.setPassword("123");
//
//        when(encoder.encode("123")).thenReturn("encoded");
//
//        String result = service.register(req);
//
//        assertEquals("Register successful", result);
//
//        verify(repo, times(1)).save(any(User.class));
//
//        // 🔥 Kafka verification
//        verify(producer, times(1)).sendUserCreatedEvent("john");
//    }
//
//    @Test
//    void login_shouldReturnUsername_whenValidCredentials() {
//
//        LoginRequest req = new LoginRequest();
//        req.setUsername("john");
//        req.setPassword("123");
//
//        User user = new User();
//        user.setUsername("john");
//        user.setPassword("encoded");
//
//        when(repo.findByUsername("john")).thenReturn(Optional.of(user));
//        when(encoder.matches("123", "encoded")).thenReturn(true);
//
//        String result = service.login(req);
//
//        assertEquals("john", result);
//    }
//
//    @Test
//    void login_shouldThrowException_whenUserNotFound() {
//
//        LoginRequest req = new LoginRequest();
//        req.setUsername("john");
//
//        when(repo.findByUsername("john")).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> service.login(req));
//    }
//
//    @Test
//    void login_shouldThrowException_whenPasswordInvalid() {
//
//        LoginRequest req = new LoginRequest();
//        req.setUsername("john");
//        req.setPassword("wrong");
//
//        User user = new User();
//        user.setUsername("john");
//        user.setPassword("encoded");
//
//        when(repo.findByUsername("john")).thenReturn(Optional.of(user));
//        when(encoder.matches("wrong", "encoded")).thenReturn(false);
//
//        assertThrows(RuntimeException.class, () -> service.login(req));
//    }
//}