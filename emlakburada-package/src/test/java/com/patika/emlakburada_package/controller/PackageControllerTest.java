package com.patika.emlakburada_package.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.dto.response.PackageResponse;
import com.patika.emlakburada_package.service.PackageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackageController.class)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageService packageService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @DisplayName("Should save a package successfully")
    void save() throws Exception {
        // Given
        PackageRequest packageRequest = new PackageRequest(); // Setup your PackageRequest as needed
        PackageResponse packageResponse = new PackageResponse(); // Setup your PackageResponse as needed

        Mockito.when(packageService.save(any(PackageRequest.class))).thenReturn(ResponseEntity.ok(packageResponse));

        String body = objectMapper.writeValueAsString(packageRequest);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/packages")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk()); // You may need to change to isCreated() if save returns a 201 status

        verify(packageService, times(1)).save(any(PackageRequest.class));
    }

    @Test
    @DisplayName("Should return all packages successfully")
    void findAll() throws Exception {
        // Given
        PackageResponse packageResponse1 = new PackageResponse(); // Setup as needed
        PackageResponse packageResponse2 = new PackageResponse(); // Setup as needed
        List<PackageResponse> packageResponses = List.of(packageResponse1, packageResponse2);

        Mockito.when(packageService.findAll()).thenReturn(ResponseEntity.ok(packageResponses));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/packages")
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    // Optional: Validate response content, e.g., using assertions on responseBody
                    assertTrue(responseBody.contains("name")); // Customize this
                });

        verify(packageService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return package by id successfully")
    void findById() throws Exception {
        // Given
        Long id = 1L;
        PackageResponse packageResponse = new PackageResponse(); // Setup as needed

        Mockito.when(packageService.findById(id)).thenReturn(ResponseEntity.ok(packageResponse));

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/packages/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    // Optional: Validate response content, e.g., using assertions on responseBody
                    assertTrue(responseBody.contains("name")); // Customize this
                });

        verify(packageService, times(1)).findById(id);
    }
}
