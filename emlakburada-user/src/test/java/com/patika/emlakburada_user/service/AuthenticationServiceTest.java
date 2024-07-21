package com.patika.emlakburada_user.service;

import com.patika.emlakburada_user.dto.request.UserNotificationRequest;
import com.patika.emlakburada_user.dto.request.UserRequest;
import com.patika.emlakburada_user.dto.request.enums.NotificationType;
import com.patika.emlakburada_user.dto.response.AuthenticationResponse;
import com.patika.emlakburada_user.dto.response.UserResponse;
import com.patika.emlakburada_user.entity.User;
import com.patika.emlakburada_user.exception.EmlakBuradaException;
import com.patika.emlakburada_user.queue.RabbitMqService;
import com.patika.emlakburada_user.repository.UserRepository;
import com.patika.emlakburada_user.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RabbitMqService rabbitMqService;

    @Test
    void register_successfully() {
        // Given
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = new User();
        user.setId(1L);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setListingRights(0);
        user.setEndDateOfPackage(null);
        user.setIsPrioritized(false);

        UserRequest userRequest = new UserRequest(email, password);
        UserResponse expectedResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getListingRights(), user.getEndDateOfPackage(), user.getIsPrioritized());

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Use ArgumentCaptor to capture the argument passed to sendNotification
        ArgumentCaptor<UserNotificationRequest> notificationCaptor = ArgumentCaptor.forClass(UserNotificationRequest.class);

        // When
        ResponseEntity<UserResponse> response = authenticationService.register(fullName, email, password);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        // Verify that sendNotification was called with the correct argument
        verify(rabbitMqService, times(1)).sendNotification(notificationCaptor.capture());

        UserNotificationRequest capturedRequest = notificationCaptor.getValue();
        assertEquals(fullName, capturedRequest.getFullName());
        assertEquals(email, capturedRequest.getEmail());
        assertEquals(0, capturedRequest.getListingRights());
        assertEquals(NotificationType.EMAIL, capturedRequest.getNotificationType());

        verify(userRepository, times(1)).findUserByEmail(email);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void register_shouldThrowException_whenUserAlreadyExists() {
        // Given
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String password = "password";
        User existingUser = new User();
        existingUser.setEmail(email);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(existingUser));

        // When & Then
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> {
            authenticationService.register(fullName, email, password);
        });

        assertEquals("A user with this email already exists.: " + email, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(rabbitMqService, never()).sendNotification(any(UserNotificationRequest.class));
    }

    @Test
    void login_successfully() {
        // Given
        String email = "john.doe@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String token = "dummy-token";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        UserRequest userRequest = new UserRequest(email, password);
        AuthenticationResponse expectedResponse = new AuthenticationResponse(token, 1L);

        // Mocking the behavior of dependencies
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn(token);

        // When
        ResponseEntity<AuthenticationResponse> response = authenticationService.login(userRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert that the actual response body matches the expected response
        AuthenticationResponse actualResponse = response.getBody();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
        assertEquals(expectedResponse.getUserId(), actualResponse.getUserId());

        // Verify interactions with mocks
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtUtil, times(1)).generateToken(user);
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // Given
        String email = "john.doe@example.com";
        String password = "password";

        UserRequest userRequest = new UserRequest(email, password);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> {
            authenticationService.login(userRequest);
        });

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtUtil, never()).generateToken(any(User.class));
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid() {
        // Given
        String email = "john.doe@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        UserRequest userRequest = new UserRequest(email, password);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // When & Then
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> {
            authenticationService.login(userRequest);
        });

        assertEquals("Invalid password", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(jwtUtil, never()).generateToken(any(User.class));
    }
}
