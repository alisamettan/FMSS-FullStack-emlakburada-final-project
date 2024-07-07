package com.patika.emlakburada_advert.client.user;


import com.patika.emlakburada_advert.client.user.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "emlakburada-user",url = "localhost:8082/emlakburada/api/v1/user")
public interface UserClient {

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> findById(@PathVariable Long userId);

    @PutMapping("/{userId}/listing-rights")
    void updateListingRights(@PathVariable Long userId, @RequestParam int listingRights) ;

}
