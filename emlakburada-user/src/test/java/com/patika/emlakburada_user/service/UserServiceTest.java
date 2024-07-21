package com.patika.emlakburada_user.service;

import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.exception.EmlakBuradaException;
import com.patika.emlakburada_user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_successfully() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setListingRights(10);
        user.setEndDateOfPackage(LocalDate.now());
        user.setIsPrioritized(true);

        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);

        // When
        ResponseEntity<List<UserResponse>> response = userService.findAll();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        UserResponse userResponse = response.getBody().get(0);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getFullName(), userResponse.getFullName());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getListingRights(), userResponse.getListingRights());
        assertEquals(user.getEndDateOfPackage(), userResponse.getEndDateOfPackage());
        assertEquals(user.getIsPrioritized(), userResponse.getIsPrioritized());
    }

    @Test
    void findById_successfully() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setListingRights(10);
        user.setEndDateOfPackage(LocalDate.now());
        user.setIsPrioritized(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<UserResponse> response = userService.findById(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        UserResponse userResponse = response.getBody();
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getFullName(), userResponse.getFullName());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getListingRights(), userResponse.getListingRights());
        assertEquals(user.getEndDateOfPackage(), userResponse.getEndDateOfPackage());
        assertEquals(user.getIsPrioritized(), userResponse.getIsPrioritized());
    }

    @Test
    void findById_userNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException thrown = assertThrows(
                EmlakBuradaException.class,
                () -> userService.findById(1L)
        );
        assertEquals("User with given id cannot be found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void updateListingRights_successfully() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setListingRights(10);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        userService.updateListingRights(1L, 20);

        // Then
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        assertEquals(20, user.getListingRights());
    }

    @Test
    void updateListingRights_userNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException thrown = assertThrows(
                EmlakBuradaException.class,
                () -> userService.updateListingRights(1L, 20)
        );
        assertEquals("User with given id cannot be found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void updateUser_successfully() {
        // Given
        UserResponse userResponse = new UserResponse(1L, "John Doe", "john.doe@example.com", 10, LocalDate.now(), true);
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        userService.updateUser(userResponse);

        // Then
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        assertEquals(userResponse.getListingRights(), user.getListingRights());
        assertEquals(userResponse.getEndDateOfPackage(), user.getEndDateOfPackage());
        assertEquals(userResponse.getIsPrioritized(), user.getIsPrioritized());
    }

    @Test
    void updateUser_userNotFound() {
        // Given
        UserResponse userResponse = new UserResponse(1L, "John Doe", "john.doe@example.com", 10, LocalDate.now(), true);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException thrown = assertThrows(
                EmlakBuradaException.class,
                () -> userService.updateUser(userResponse)
        );
        assertEquals("User with given id cannot be found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }
}
