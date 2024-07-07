package com.patika.emlakburada_order.client.userpackage;


import com.patika.emlakburada_order.client.user.response.UserResponse;
import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageService {

    private final PackageClient packageClient;

    public PackageResponse getPackageById(Long id){
        ResponseEntity<PackageResponse> response =packageClient.findById(id);

        if(response.getStatusCode()!= HttpStatus.OK){
            log.info("package cannot be found..");
        }

        return response.getBody();
    }
}
