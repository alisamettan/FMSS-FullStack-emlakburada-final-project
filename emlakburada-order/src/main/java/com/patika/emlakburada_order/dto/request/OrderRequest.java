package com.patika.emlakburada_order.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private Long packageId;
    private Long cardNumber;
    private String expiration;
    private Integer cvc;
}
