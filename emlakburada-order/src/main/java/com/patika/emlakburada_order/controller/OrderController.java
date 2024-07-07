package com.patika.emlakburada_order.controller;

import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest orderRequest){
        return orderService.save(orderRequest);
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<OrderResponse>> findByUserId(@PathVariable Long userId){
        return orderService.findAllByUserId(userId);
    }
}
