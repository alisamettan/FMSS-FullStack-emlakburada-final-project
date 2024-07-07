package com.patika.emlakburada_order.service;

import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderResponse> save(OrderRequest request);
    ResponseEntity<List<OrderResponse>> findAllByUserId(Long userId);
}
