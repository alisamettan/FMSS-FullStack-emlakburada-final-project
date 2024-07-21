package com.patika.emlakburada_package.service.impl;

import com.patika.emlakburada_package.converter.PackageConverter;
import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.dto.response.PackageResponse;
import com.patika.emlakburada_package.entity.UserPackage;
import com.patika.emlakburada_package.entity.enums.PackageType;
import com.patika.emlakburada_package.exception.EmlakBuradaException;
import com.patika.emlakburada_package.repository.PackageRepository;
import com.patika.emlakburada_package.service.PackageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackageServiceImplTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageServiceImpl packageService;

    @Test
    @DisplayName("Should return all packages successfully")
    void findAll() {
        // Given
        UserPackage userPackage = new UserPackage(1L, "Basic", PackageType.ECO, 100.0, 10);
        List<UserPackage> userPackages = new ArrayList<>();
        userPackages.add(userPackage);

        when(packageRepository.findAll()).thenReturn(userPackages);

        // When
        ResponseEntity<List<PackageResponse>> response = packageService.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Basic", response.getBody().get(0).getName());
    }

    @Test
    @DisplayName("Should save package successfully")
    void save() {
        // Given
        PackageRequest request = new PackageRequest("Package Name", PackageType.ECO, 100.0, 10);
        UserPackage userPackage = new UserPackage();
        userPackage.setId(1L);
        userPackage.setName("Package Name");
        userPackage.setType(PackageType.ECO);
        userPackage.setPrice(100.0);
        userPackage.setListingRights(10);

        Mockito.when(packageRepository.save(Mockito.any(UserPackage.class))).thenReturn(userPackage);

        // When
        ResponseEntity<PackageResponse> response = packageService.save(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PackageResponse packageResponse = response.getBody();
        assertNotNull(packageResponse);
        assertEquals(1L, packageResponse.getId());
        assertEquals("Package Name", packageResponse.getName());
        assertEquals(PackageType.ECO, packageResponse.getPackageType());
        assertEquals(100.0, packageResponse.getPrice(), 0.01);
        assertEquals(10, packageResponse.getListingRights());
    }

    @Test
    @DisplayName("Should throw exception when package not found by id")
    void findById_NotFound() {
        // Given
        Long id = 1L;
        when(packageRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> packageService.findById(id));
        assertEquals("Package with given id cannot be found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Should return package by id successfully")
    void findById() {
        // Given
        Long id = 1L;
        UserPackage userPackage = new UserPackage(id, "Basic", PackageType.ECO, 100.0, 10);
        when(packageRepository.findById(id)).thenReturn(Optional.of(userPackage));

        // When
        ResponseEntity<PackageResponse> response = packageService.findById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("Basic", response.getBody().getName());
    }
}
