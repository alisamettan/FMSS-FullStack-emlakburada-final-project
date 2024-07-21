package com.patika.emlakburada_advert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.request.AdvertSearchRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.service.AdvertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdvertController.class)
class AdvertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertService advertService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Find all adverts successfully")
    @Test
    void findAll() throws Exception {
        // Given
        AdvertSearchRequest searchRequest = prepareAdvertSearchRequest();
        Map<String, Object> response = Map.of("adverts", Collections.emptyList()); // or provide actual data if needed

        // Mock service
        Mockito.when(advertService.findAll(Mockito.any(AdvertSearchRequest.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(get("/adverts")
                .content(objectMapper.writeValueAsString(searchRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).findAll(Mockito.any(AdvertSearchRequest.class));
    }

    @DisplayName("Save advert successfully")
    @Test
    void save() throws Exception {
        // Given
        AdvertRequest request = prepareAdvertRequest();
        AdvertResponse advertResponse = new AdvertResponse(); // Add actual response details if needed

        // Mock service
        Mockito.when(advertService.save(Mockito.any(AdvertRequest.class)))
                .thenReturn(new ResponseEntity<>(advertResponse, HttpStatus.CREATED));

        // When
        ResultActions resultActions = mockMvc.perform(post("/adverts")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).save(Mockito.any(AdvertRequest.class));
    }

    @DisplayName("Find advert by ID successfully")
    @Test
    void findById() throws Exception {
        // Given
        Long id = 1L;
        AdvertResponse advertResponse = new AdvertResponse(); // Add actual response details if needed

        // Mock service
        Mockito.when(advertService.findById(id))
                .thenReturn(new ResponseEntity<>(advertResponse, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(get("/adverts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).findById(id);
    }

    @DisplayName("Find adverts by user ID successfully")
    @Test
    void findByUserId() throws Exception {
        // Given
        Long userId = 1L;
        List<AdvertResponse> advertResponses = Collections.emptyList(); // or provide actual data if needed

        // Mock service
        Mockito.when(advertService.findByUserId(userId))
                .thenReturn(new ResponseEntity<>(advertResponses, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(get("/adverts/find-advert-byUserId/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).findByUserId(userId);
    }

    @DisplayName("Delete advert successfully")
    @Test
    void delete() throws Exception {
        // Given
        Long id = 1L;
        AdvertResponse advertResponse = new AdvertResponse(); // Add actual response details if needed

        // Mock service
        Mockito.when(advertService.delete(id))
                .thenReturn(new ResponseEntity<>(advertResponse, HttpStatus.OK));


        verify(advertService, times(1)).delete(id);
    }

    @DisplayName("Update advert successfully")
    @Test
    void update() throws Exception {
        // Given
        Long id = 1L;
        AdvertRequest request = prepareAdvertRequest();
        AdvertResponse advertResponse = new AdvertResponse(); // Add actual response details if needed

        // Mock service
        Mockito.when(advertService.update(id, request))
                .thenReturn(new ResponseEntity<>(advertResponse, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(put("/adverts/update/{id}", id)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).update(id, request);
    }

    @DisplayName("Update advert status to ACTIVE successfully")
    @Test
    void updateStatusActiveById() throws Exception {
        // Given
        Long id = 1L;
        Advert advert = new Advert(); // Add actual Advert details if needed

        // Mock service
        Mockito.when(advertService.updateStatusActiveById(id))
                .thenReturn(new ResponseEntity<>(advert, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(put("/adverts/update-status-active/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).updateStatusActiveById(id);
    }

    @DisplayName("Update advert status to PASSIVE successfully")
    @Test
    void updateStatusPassiveById() throws Exception {
        // Given
        Long id = 1L;
        Advert advert = new Advert(); // Add actual Advert details if needed

        // Mock service
        Mockito.when(advertService.updateStatusPassiveById(id))
                .thenReturn(new ResponseEntity<>(advert, HttpStatus.OK));

        // When
        ResultActions resultActions = mockMvc.perform(put("/adverts/update-status-passive/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(advertService, times(1)).updateStatusPassiveById(id);
    }

    // Utility Methods
    private AdvertSearchRequest prepareAdvertSearchRequest() {
        AdvertSearchRequest searchRequest = new AdvertSearchRequest();
        searchRequest.setTitle("advert1");
        searchRequest.setSort("price:asc");
        searchRequest.setPage(0);
        searchRequest.setSize(12);
        return searchRequest;
    }

    private AdvertRequest prepareAdvertRequest() {
        AdvertRequest request = new AdvertRequest();
        request.setTitle("advert1");
        request.setDescription("Advert description");
        request.setPrice(100.0);
        return request;
    }
}
