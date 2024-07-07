package com.patika.emlakburada_package.service;

import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.dto.response.PackageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PackageService {

    ResponseEntity<List<PackageResponse>> findAll();
    ResponseEntity<PackageResponse> save(PackageRequest request);
    ResponseEntity<PackageResponse> findById(Long id);

}
