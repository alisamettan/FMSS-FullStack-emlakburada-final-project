package com.patika.emlakburada_user.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.patika.emlakburada_user.dto.request.UserRequest;
import com.patika.emlakburada_user.dto.response.AuthenticationResponse;
import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser // Mock user for security context
    void register_ShouldReturnOkStatus() throws Exception {
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        UserResponse userResponse = new UserResponse(1L, "John Doe", "john.doe@example.com", 10, LocalDate.now().plusDays(5), false);
        when(authenticationService.register(user.getFullName(), user.getEmail(), user.getPassword()))
                .thenReturn(ResponseEntity.ok(userResponse));

        String body = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/auth/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(authenticationService, times(1)).register(user.getFullName(), user.getEmail(), user.getPassword());
    }

    @Test
    @WithMockUser // Mock user for security context
    void login_ShouldReturnOkStatus() throws Exception {
        UserRequest userRequest = new UserRequest("john.doe@example.com", "password");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse("dummy-token", 1L);
        when(authenticationService.login(userRequest))
                .thenReturn(ResponseEntity.ok(authenticationResponse));

        String body = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/auth/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Add CSRF token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(authenticationService, times(1)).login(Mockito.any(userRequest.getClass()));
    }
}
