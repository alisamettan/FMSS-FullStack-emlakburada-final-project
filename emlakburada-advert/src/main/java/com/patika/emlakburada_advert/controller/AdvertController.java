package com.patika.emlakburada_advert.controller;


import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.service.AdvertService;
import jakarta.ws.rs.DELETE;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;
    
    @PostMapping
    public ResponseEntity<AdvertResponse> save(@RequestBody AdvertRequest request){
        return advertService.save(request);
    }

    @GetMapping
    public ResponseEntity<List<AdvertResponse>> findAll(){
        return advertService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponse> findById(@PathVariable Long id){
        return advertService.findById(id);
    }

    @GetMapping("/find-advert-byUserId/{userId}")
    public ResponseEntity<List<AdvertResponse>> findByUserId(@PathVariable Long userId){
        return advertService.findByUserId(userId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AdvertResponse> delete(@PathVariable Long id){
        return advertService.delete(id);
    }

    @PutMapping("/update-status-active/{id}")
    public ResponseEntity<Advert> updateStatusActiveById(@PathVariable Long id){
        return advertService.updateStatusActiveById(id);
    }

    @PutMapping("/update-status-passive/{id}")
    public ResponseEntity<Advert> updateStatusPassiveById(@PathVariable Long id){
        return advertService.updateStatusPassiveById(id);
    }
}
