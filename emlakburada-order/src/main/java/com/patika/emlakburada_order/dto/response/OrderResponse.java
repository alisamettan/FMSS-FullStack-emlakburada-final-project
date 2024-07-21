package com.patika.emlakburada_order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long packageId;
    private Double totalAmount;
    private String status;
    private LocalDate orderDate;
    private Long cardNumber;
    private String expiration;
    private Integer cvc;
}
