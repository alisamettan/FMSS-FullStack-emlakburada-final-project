package com.patika.emlakburada_order.service.impl;

import com.patika.emlakburada_order.client.user.UserService;
import com.patika.emlakburada_order.client.user.response.UserResponse;
import com.patika.emlakburada_order.client.userpackage.PackageService;
import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import com.patika.emlakburada_order.client.userpackage.response.enums.PackageType;

import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.entity.Order;
import com.patika.emlakburada_order.exception.EmlakBuradaException;
import com.patika.emlakburada_order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private PackageService packageService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderRequest orderRequest;
    private UserResponse userResponse;
    private PackageResponse packageResponse;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setUserId(1L);
        orderRequest.setPackageId(1L);

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setListingRights(0);

        packageResponse = new PackageResponse();
        packageResponse.setId(1L);
        packageResponse.setPackageType(PackageType.ECO);
        packageResponse.setPrice(100.0);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setPackageId(1L);
        order.setTotalAmount(100.0);
        order.setStatus("SUCCESS");
    }

    @Test
    void testSaveOrder() {
        when(userService.getUserById(orderRequest.getUserId())).thenReturn(userResponse);
        when(packageService.getPackageById(orderRequest.getPackageId())).thenReturn(packageResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        ResponseEntity<OrderResponse> response = orderService.save(orderRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
        assertEquals(order.getUserId(), response.getBody().getUserId());
        assertEquals(order.getPackageId(), response.getBody().getPackageId());
        verify(userService).updateUser(any(UserResponse.class));
    }

    @Test
    void testFindAllByUserId() {
        when(userService.getUserById(userResponse.getId())).thenReturn(userResponse);
        when(orderRepository.findAllByUserId(userResponse.getId())).thenReturn(Collections.singletonList(order));

        ResponseEntity<List<OrderResponse>> response = orderService.findAllByUserId(userResponse.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(order.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testFindAllByUserId_NotFound() {
        when(userService.getUserById(userResponse.getId())).thenReturn(userResponse);
        when(orderRepository.findAllByUserId(userResponse.getId())).thenReturn(Collections.emptyList());

        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> {
            orderService.findAllByUserId(userResponse.getId());
        });

        assertEquals("Orders with given user id cannot be found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
