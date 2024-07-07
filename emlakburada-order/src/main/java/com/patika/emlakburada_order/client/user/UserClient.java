package com.patika.emlakburada_order.client.user;

import com.patika.emlakburada_order.client.user.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "emlakburada-user",url = "localhost:8082/emlakburada/api/v1/user")
public interface UserClient {

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> findById(@PathVariable Long userId);


    @PutMapping("/updateUserFields")
    void updateUser(@RequestBody UserResponse userResponse);
}
