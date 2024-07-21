package com.patika.emlakburada_order.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(1L);
        orderRequest.setPackageId(1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setUserId(1L);
        orderResponse.setPackageId(1L);

        Mockito.when(orderService.save(Mockito.any(OrderRequest.class)))
                .thenReturn(new ResponseEntity<>(orderResponse, HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orderResponse)));
    }

    @Test
    public void testFindByUserId() throws Exception {
        Long userId = 1L;
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setUserId(1L);
        orderResponse.setPackageId(1L);

        List<OrderResponse> orderResponseList = Collections.singletonList(orderResponse);

        Mockito.when(orderService.findAllByUserId(userId))
                .thenReturn(new ResponseEntity<>(orderResponseList, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/findByUserId/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orderResponseList)));
    }
}
