package com.patika.emlakburada_package.controller;

import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.dto.response.PackageResponse;
import com.patika.emlakburada_package.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @GetMapping
    public ResponseEntity<List<PackageResponse>> findAll(){
        return packageService.findAll();
    }

    @PostMapping
    public ResponseEntity<PackageResponse> save(@RequestBody PackageRequest request){
        return packageService.save(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageResponse> findById(@PathVariable Long id){
        return packageService.findById(id);
    }
}
