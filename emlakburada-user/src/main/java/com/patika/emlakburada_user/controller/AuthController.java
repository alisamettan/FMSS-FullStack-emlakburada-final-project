package com.patika.emlakburada_user.controller;


import com.patika.emlakburada_user.dto.request.UserRequest;
import com.patika.emlakburada_user.dto.response.AuthenticationResponse;
import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody User user){
        return authenticationService.register(user.getFullName(),
                user.getEmail(),
                user.getPassword());
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRequest request) {
       return authenticationService.login(request);

    }
}
