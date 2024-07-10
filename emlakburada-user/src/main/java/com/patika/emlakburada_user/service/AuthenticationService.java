package com.patika.emlakburada_user.service;



import com.patika.emlakburada_user.dto.request.UserNotificationRequest;
import com.patika.emlakburada_user.dto.request.UserRequest;
import com.patika.emlakburada_user.dto.response.AuthenticationResponse;
import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.exception.EmlakBuradaException;
import com.patika.emlakburada_user.queue.RabbitMqService;
import com.patika.emlakburada_user.repository.UserRepository;
import com.patika.emlakburada_user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RabbitMqService rabbitMqService;

    public ResponseEntity<UserResponse> register(String fullName, String email, String password){
        Optional<User> existingUser=userRepository.findUserByEmail(email);
        if(existingUser.isPresent()){
            throw new EmlakBuradaException("A user with this email already exists.: "+email,HttpStatus.BAD_REQUEST);
        }

        String encodedPassword=passwordEncoder.encode(password);


        User user=new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setIsPrioritized(false);
        user.setEndDateOfPackage(null);

        //ilan listeleme hakkı ilk kayıt işleminde 0 olarak ayarlanıyor.
        user.setListingRights(0);
        user=userRepository.save(user);

        UserResponse userResponse=new UserResponse(user.getId(),user.getFullName(),user.getEmail(),user.getListingRights(),user.getEndDateOfPackage(),user.getIsPrioritized());

        //When user registers notification is sent to rabbitmq
        UserNotificationRequest request=new UserNotificationRequest(userResponse.getFullName(),userResponse.getEmail(),userResponse.getListingRights());
        rabbitMqService.sendNotification(request);



        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    public ResponseEntity<AuthenticationResponse> login(UserRequest request) {

        User user=userRepository.findUserByEmail(request.getEmail()).orElseThrow(()->new EmlakBuradaException("User not found",HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new EmlakBuradaException("Invalid password",HttpStatus.BAD_REQUEST);
        }

        String token=jwtUtil.generateToken(user.getEmail());
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setToken(token);

        return new ResponseEntity<>(authenticationResponse,HttpStatus.CREATED);
    }
}
