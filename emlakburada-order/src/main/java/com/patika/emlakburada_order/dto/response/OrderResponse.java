package com.patika.emlakburada_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long packageId;
    private Double totalAmount;
    private String status;
    private LocalDate orderDate;
}
