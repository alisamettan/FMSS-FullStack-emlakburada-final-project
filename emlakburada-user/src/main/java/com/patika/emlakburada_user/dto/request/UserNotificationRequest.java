package com.patika.emlakburada_user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotificationRequest {
    private String fullName;
    private String email;
    private Integer listingRights;
}
