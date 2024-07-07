package com.patika.emlakburada_user.controller;

import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long userId){
        return userService.findById(userId);
    }

    @PutMapping("/{userId}/listing-rights")
    public ResponseEntity<Void> updateListingRights(@PathVariable Long userId, @RequestParam int listingRights) {
        userService.updateListingRights(userId, listingRights);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateUserFields")
    public ResponseEntity<Void> updateUser(@RequestBody UserResponse userResponse){
        userService.updateUser(userResponse);
        return ResponseEntity.ok().build();
    }
}
