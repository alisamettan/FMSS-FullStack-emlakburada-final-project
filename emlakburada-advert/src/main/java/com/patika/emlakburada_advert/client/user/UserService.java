package com.patika.emlakburada_advert.client.user;


import com.patika.emlakburada_advert.client.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserClient userClient;

    public UserResponse getUserById(Long id){
        ResponseEntity<UserResponse> response =userClient.findById(id);

        if(response.getStatusCode()!= HttpStatus.OK){
            log.info("publisher bulunamadı..");
        }

        return response.getBody();
    }

    public void updateListingRights(Long userId, int listingRights) {
        userClient.updateListingRights(userId, listingRights);
    }

}
