package com.patika.emlakburada_advert.service.impl;

import com.patika.emlakburada_advert.client.user.UserService;
import com.patika.emlakburada_advert.client.user.response.UserResponse;
import com.patika.emlakburada_advert.converter.AdvertConverter;
import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.request.AdvertSearchRequest;
import com.patika.emlakburada_advert.dto.request.AdvertStatusRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.entity.Image;
import com.patika.emlakburada_advert.entity.enums.AdvertStatus;
import com.patika.emlakburada_advert.exception.EmlakBuradaException;
import com.patika.emlakburada_advert.queue.RabbitMqService;
import com.patika.emlakburada_advert.repository.AdvertRepository;
import com.patika.emlakburada_advert.repository.ImageRepository;
import com.patika.emlakburada_advert.repository.specification.AdvertSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertServiceImplTest {

    @InjectMocks
    private AdvertServiceImpl advertService;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private UserService userService;

    @Mock
    private RabbitMqService rabbitMqService;

    @Mock
    private ImageRepository imageRepository;

    @Test
    @DisplayName("Should save advert successfully")
    void saveAdvert() {
        // Given
        AdvertRequest request = new AdvertRequest();
        request.setUserId(1L);
        UserResponse userResponse = new UserResponse();
        userResponse.setListingRights(5);
        userResponse.setEndDateOfPackage(LocalDate.now().plusDays(10));
        Advert advert = new Advert();
        AdvertResponse advertResponse = new AdvertResponse();

        Mockito.when(userService.getUserById(request.getUserId())).thenReturn(userResponse);
        Mockito.when(advertRepository.save(any(Advert.class))).thenReturn(advert);
        Mockito.when(AdvertConverter.toAdvertResponse(advert)).thenReturn(advertResponse);

        // When
        ResponseEntity<AdvertResponse> response = advertService.save(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(advertResponse, response.getBody());
        verify(userService, times(1)).updateListingRights(request.getUserId(), userResponse.getListingRights() - 1);
    }

    @Test
    @DisplayName("Should throw exception if user has no listing rights")
    void saveAdvert_NoListingRights() {
        // Given
        AdvertRequest request = new AdvertRequest();
        request.setUserId(1L);
        UserResponse userResponse = new UserResponse();
        userResponse.setListingRights(0);
        userResponse.setEndDateOfPackage(LocalDate.now().plusDays(10));

        when(userService.getUserById(request.getUserId())).thenReturn(userResponse);

        // When & Then
        Exception exception = assertThrows(EmlakBuradaException.class, () -> advertService.save(request));
        assertEquals("User doesn't have enough listing rights", exception.getMessage());
    }

    @Test
    @DisplayName("Should return all adverts successfully")
    void findAll() {
        // Given
        Advert advert = new Advert();
        advert.setId(1L);
        advert.setTitle("Ev");

        AdvertResponse advertResponse = new AdvertResponse();
        advertResponse.setId(advert.getId());
        advertResponse.setTitle(advert.getTitle());

        Page<Advert> advertPage = new PageImpl<>(Collections.singletonList(advert));
        AdvertSearchRequest searchRequest = new AdvertSearchRequest();
        searchRequest.setPage(0);
        searchRequest.setSize(10);

        // Mock the repository call to return the paginated list of adverts
        Mockito.when(advertRepository.findAll(
                ArgumentMatchers.any(Specification.class),
                ArgumentMatchers.any(Pageable.class)
        )).thenReturn(advertPage);

        // Mock the converter to return the advertResponse
        Mockito.when(AdvertConverter.toAdvertResponse(advert)).thenReturn(advertResponse);

        // Call the service method
        ResponseEntity<Map<String, Object>> response = advertService.findAll(searchRequest);

        // Extract the body from the response
        Map<String, Object> responseBody = response.getBody();

        // Then
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(responseBody.containsKey("adverts"));
        assertTrue(responseBody.containsKey("totalCount"));

        // Validate the adverts list
        List<AdvertResponse> adverts = (List<AdvertResponse>) responseBody.get("adverts");
        assertNotNull(adverts);
        assertEquals(1, adverts.size());
        assertEquals("Ev", adverts.get(0).getTitle());

        // Validate the total count
        assertEquals(1, responseBody.get("totalCount"));
    }


    @Test
    @DisplayName("Should find advert by id successfully")
    void findById() {
        // Given
        Long id = 1L;
        Advert advert = new Advert();
        AdvertResponse advertResponse = new AdvertResponse();

        when(advertRepository.findById(id)).thenReturn(Optional.of(advert));
        when(AdvertConverter.toAdvertResponse(advert)).thenReturn(advertResponse);

        // When
        ResponseEntity<AdvertResponse> response = advertService.findById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertResponse, response.getBody());
    }

    @Test
    @DisplayName("Should throw exception if advert not found by id")
    void findById_NotFound() {
        // Given
        Long id = 1L;

        when(advertRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(EmlakBuradaException.class, () -> advertService.findById(id));
        assertEquals("Advert with given id cannot be found", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete advert successfully")
    void delete() {
        // Given
        Long id = 1L;
        Advert advert = new Advert();
        AdvertResponse advertResponse = new AdvertResponse();

        // Mock the repository call
        when(advertRepository.findById(id)).thenReturn(Optional.of(advert));

        // Mock the conversion call
        when(AdvertConverter.toAdvertResponse(advert)).thenReturn(advertResponse);

        // When
        ResponseEntity<AdvertResponse> response = advertService.delete(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertResponse, response.getBody());
        verify(advertRepository, times(1)).delete(Mockito.any(Advert.class));
    }

    @Test
    @DisplayName("Should update advert successfully")
    void update() {
        // Given
        Long id = 1L;
        AdvertRequest request = new AdvertRequest();
        Advert existingAdvert = new Advert();
        Advert updatedAdvert = new Advert();
        AdvertResponse advertResponse = new AdvertResponse();

        Mockito.when(advertRepository.findById(id)).thenReturn(Optional.of(existingAdvert));
        Mockito.when(AdvertConverter.toUpdatedAdvert(request, existingAdvert)).thenReturn(updatedAdvert);
        Mockito.when(advertRepository.save(updatedAdvert)).thenReturn(updatedAdvert);
        Mockito.when(AdvertConverter.toAdvertResponse(updatedAdvert)).thenReturn(advertResponse);

        // When
        ResponseEntity<AdvertResponse> response = advertService.update(id, request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(advertResponse, response.getBody());
        verify(advertRepository, times(1)).save(updatedAdvert);
    }

    @Test
    @DisplayName("Should update advert status to active successfully")
    void updateStatusActiveById() {
        // Given
        Long id = 1L;
        Advert advert = new Advert();
        advert.setAdvertStatus(AdvertStatus.PASSIVE);
        AdvertStatusRequest request = new AdvertStatusRequest();
        request.setId(id);
        request.setAdvertStatus(AdvertStatus.ACTIVE);

        when(advertRepository.findById(id)).thenReturn(Optional.of(advert));

        // When
        ResponseEntity<Advert> response = advertService.updateStatusActiveById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AdvertStatus.ACTIVE, response.getBody().getAdvertStatus());
        verify(rabbitMqService, times(1)).sendNotification(Mockito.any(request.getClass()));

    }

    @Test
    void updateStatusActiveById_AdvertNotFound() {
        // Given
        Long advertId = 1L;

        when(advertRepository.findById(advertId)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException thrown = assertThrows(EmlakBuradaException.class, () -> {
            advertService.updateStatusActiveById(advertId);
        });

        assertEquals("Advert with given id cannot be found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }


    @Test
    @DisplayName("Should update advert status to passive successfully")
    void updateStatusPassiveById() {
        // Given
        Long id = 1L;
        Advert advert = new Advert();
        advert.setAdvertStatus(AdvertStatus.ACTIVE);
        AdvertStatusRequest request = new AdvertStatusRequest();
        request.setId(id);
        request.setAdvertStatus(AdvertStatus.PASSIVE);

        when(advertRepository.findById(id)).thenReturn(Optional.of(advert));

        // When
        ResponseEntity<Advert> response = advertService.updateStatusPassiveById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AdvertStatus.PASSIVE, response.getBody().getAdvertStatus());
        verify(rabbitMqService, times(1)).sendNotification(Mockito.any(request.getClass()));

    }
    @Test
    void updateStatusPassiveById_AdvertNotFound() {
        // Given
        Long advertId = 1L;

        when(advertRepository.findById(advertId)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException thrown = assertThrows(EmlakBuradaException.class, () -> {
            advertService.updateStatusPassiveById(advertId);
        });

        assertEquals("Advert with given id cannot be found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }
}
