package com.patika.emlakburada_advertstatus.service.impl;

import com.patika.emlakburada_advertstatus.dto.request.AdvertStatusRequest;
import com.patika.emlakburada_advertstatus.entity.Advert;
import com.patika.emlakburada_advertstatus.entity.enums.AdvertStatus;
import com.patika.emlakburada_advertstatus.repository.AdvertRepository;
import com.patika.emlakburada_advertstatus.service.AdvertStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertStatusServiceImplTest {

    @Mock
    private AdvertRepository advertRepository;

    @InjectMocks
    private AdvertStatusServiceImpl advertStatusService;

    @Test
    void updateStatus_ShouldUpdateAdvertStatus() {
        // Given
        Long advertId = 1L;
        AdvertStatusRequest request = new AdvertStatusRequest();
        request.setId(advertId);
        request.setAdvertStatus(AdvertStatus.ACTIVE);

        Advert advert = new Advert();
        advert.setId(advertId);
        advert.setAdvertStatus(AdvertStatus.PASSIVE); // Initial status

        Mockito.when(advertRepository.findById(anyLong())).thenReturn(Optional.of(advert));
        Mockito.when(advertRepository.save(any(Advert.class))).thenReturn(advert);

        // When
        advertStatusService.updateStatus(request);

        // Then
        verify(advertRepository).findById(advertId);
        verify(advertRepository).save(advert);
        assertEquals(AdvertStatus.ACTIVE, advert.getAdvertStatus());
    }
}
