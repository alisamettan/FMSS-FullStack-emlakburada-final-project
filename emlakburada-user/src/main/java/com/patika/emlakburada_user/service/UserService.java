package com.patika.emlakburada_user.service;


import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.exception.EmlakBuradaException;
import com.patika.emlakburada_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(()->{
                    System.out.println("User credentials are not valid");
                    throw new EmlakBuradaException("User credentials are not valid",HttpStatus.BAD_REQUEST);
                });
    }

    public ResponseEntity<List<UserResponse>> findAll(){
        List<UserResponse> userResponses=new ArrayList<>();

        for(User user:userRepository.findAll()){
            UserResponse userResponse=new UserResponse(user.getId(), user.getFullName(), user.getEmail(),user.getListingRights(),user.getEndDateOfPackage(),user.getIsPrioritized());
            userResponses.add(userResponse);
        }

        return new ResponseEntity<>(userResponses, HttpStatus.OK);

    }

    public ResponseEntity<UserResponse> findById(Long userId){
        Optional<User> userOptional=userRepository.findById(userId);

        if(userOptional.isEmpty()){
            throw new EmlakBuradaException("User with given id cannot be found",HttpStatus.NOT_FOUND);
        }
        User user=userOptional.get();

        UserResponse userResponse=new UserResponse(user.getId(),user.getFullName(),user.getEmail(),user.getListingRights(),user.getEndDateOfPackage(),user.getIsPrioritized());

        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    public void updateListingRights(Long userId,int listingRights){
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new EmlakBuradaException("User with given id cannot be found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        user.setListingRights(listingRights);
        userRepository.save(user);
    }

    public void updateUser(UserResponse userResponse) {
        Optional<User> userOptional = userRepository.findById(userResponse.getId());

        if (userOptional.isEmpty()) {
            throw new EmlakBuradaException("User with given id cannot be found", HttpStatus.NOT_FOUND);
        }

        User user=userOptional.get();
        user.setListingRights(userResponse.getListingRights());
        user.setEndDateOfPackage(userResponse.getEndDateOfPackage());
        user.setIsPrioritized(userResponse.getIsPrioritized());
        userRepository.save(user);
    }
}