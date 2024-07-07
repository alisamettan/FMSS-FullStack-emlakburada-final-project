package com.patika.emlakburada_order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private Long packageId;
}
