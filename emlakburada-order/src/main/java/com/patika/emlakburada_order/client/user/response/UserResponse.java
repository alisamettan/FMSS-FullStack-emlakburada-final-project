package com.patika.emlakburada_order.client.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private int listingRights;
    private LocalDate endDateOfPackage;
    private Boolean isPrioritized;
}
