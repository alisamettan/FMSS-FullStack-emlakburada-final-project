package com.patika.emlakburada_package.service.impl;

import com.patika.emlakburada_package.converter.PackageConverter;
import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.dto.response.PackageResponse;
import com.patika.emlakburada_package.entity.UserPackage;
import com.patika.emlakburada_package.exception.EmlakBuradaException;
import com.patika.emlakburada_package.repository.PackageRepository;
import com.patika.emlakburada_package.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {


    private final PackageRepository packageRepository;

    @Override
    public ResponseEntity<List<PackageResponse>> findAll() {
        List<PackageResponse> packageResponses=new ArrayList<>();

        for(UserPackage userPackage :packageRepository.findAll()){
            PackageResponse packageResponse=new PackageResponse(userPackage.getId(),userPackage.getName(),userPackage.getType(),userPackage.getPrice());
            packageResponses.add(packageResponse);
        }
        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PackageResponse> save(PackageRequest request) {
        UserPackage userPackage= PackageConverter.toPackage(request);

        userPackage=packageRepository.save(userPackage);

        PackageResponse packageResponse=new PackageResponse(userPackage.getId(),userPackage.getName(),userPackage.getType(),userPackage.getPrice());

        return new ResponseEntity<>(packageResponse,HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<PackageResponse> findById(Long id){
        Optional<UserPackage> packageOptional=packageRepository.findById(id);

        if(packageOptional.isEmpty()){
            throw new EmlakBuradaException("Package with given id cannot be found",HttpStatus.NOT_FOUND);
        }
        UserPackage userPackage=packageOptional.get();
        PackageResponse packageResponse=new PackageResponse(userPackage.getId(),userPackage.getName(),userPackage.getType(),userPackage.getPrice());

        return new ResponseEntity<>(packageResponse,HttpStatus.OK);
    }


}
