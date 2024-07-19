package com.patika.emlakburada_advert.service;

import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.request.AdvertSearchRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdvertService {
    ResponseEntity<AdvertResponse> save(AdvertRequest request);
    ResponseEntity<List<AdvertResponse>> findAll(AdvertSearchRequest request);
    ResponseEntity<AdvertResponse> findById(Long id);
    ResponseEntity<List<AdvertResponse>> findByUserId(Long userId);
    ResponseEntity<AdvertResponse> delete(Long id);
    ResponseEntity<Advert> updateStatusActiveById(Long id);
    ResponseEntity<Advert> updateStatusPassiveById(Long id);
    ResponseEntity<AdvertResponse> update(Long id,AdvertRequest request);


}
