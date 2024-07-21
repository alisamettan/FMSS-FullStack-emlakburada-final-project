package com.patika.emlakburada_advert.client.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private int listingRights;
    private LocalDate endDateOfPackage;
    private Boolean isPrioritized;
}
