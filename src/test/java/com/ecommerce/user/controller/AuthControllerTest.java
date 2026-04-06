//package com.ecommerce.user.controller;
//
//import com.ecommerce.user.dto.LoginRequest;
//import com.ecommerce.user.security.JwtUtil;
//import com.ecommerce.user.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService service;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void register_shouldReturnSuccess() throws Exception {
//
//        Mockito.when(service.register(Mockito.any()))
//                .thenReturn("Register successful");
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(new LoginRequest())))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void login_shouldReturnToken() throws Exception {
//
//        LoginRequest req = new LoginRequest();
//        req.setUsername("john");
//        req.setPassword("123");
//
//        Mockito.when(service.login(Mockito.any())).thenReturn("john");
//        Mockito.when(jwtUtil.generateToken("john")).thenReturn("token123");
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("token123"));
//    }
//}