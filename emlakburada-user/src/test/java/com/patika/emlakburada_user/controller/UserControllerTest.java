package com.patika.emlakburada_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private String generateJwtToken() {
        // Simülasyon amaçlı basit bir token döndür
        return "test.jwt.token";
    }

    @Test
    void findAll() throws Exception {
        List<UserResponse> userList = new ArrayList<>();
        userList.add(new UserResponse(1L, "Ali", "alisamet@gmail.com", 10, LocalDate.now(), false));

        when(userService.findAll()).thenReturn(new ResponseEntity<>(userList, HttpStatus.OK));

        mockMvc.perform(get("/user")
                        .header(HttpHeaders.AUTHORIZATION, generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].fullName").value("Ali"))
                .andExpect(jsonPath("$[0].email").value("alisamet@gmail.com"))
                .andExpect(jsonPath("$[0].listingRights").value(10))
                .andExpect(jsonPath("$[0].isPrioritized").value(false));
    }

    @Test
    void findById() throws Exception {
        UserResponse userResponse = new UserResponse(1L, "Ali", "alisamet@gmail.com", 10, LocalDate.now(), false);

        when(userService.findById(1L)).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));

        mockMvc.perform(get("/user/1")
                        .header(HttpHeaders.AUTHORIZATION, generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Ali"))
                .andExpect(jsonPath("$.email").value("alisamet@gmail.com"))
                .andExpect(jsonPath("$.listingRights").value(10))
                .andExpect(jsonPath("$.isPrioritized").value(false));
    }

    @Test
    void updateListingRights() throws Exception {
        doNothing().when(userService).updateListingRights(1L, 10);

        mockMvc.perform(put("/user/1/listing-rights")
                        .param("listingRights", "10")
                        .header(HttpHeaders.AUTHORIZATION, generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateListingRights(1L, 10);
    }

    @Test
    void updateUser() throws Exception {
        doNothing().when(userService).updateUser(any(UserResponse.class));

        UserResponse userResponse = new UserResponse(1L, "Ali", "alisamet@gmail.com", 10, LocalDate.now(), false);

        mockMvc.perform(put("/user/updateUserFields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userResponse))
                        .header(HttpHeaders.AUTHORIZATION, generateJwtToken()))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(any(UserResponse.class));
    }
}
