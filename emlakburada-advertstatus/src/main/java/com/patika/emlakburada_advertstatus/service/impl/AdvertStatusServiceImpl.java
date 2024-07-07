package com.patika.emlakburada_advertstatus.service.impl;



import com.patika.emlakburada_advertstatus.dto.request.AdvertStatusRequest;
import com.patika.emlakburada_advertstatus.entity.Advert;
import com.patika.emlakburada_advertstatus.repository.AdvertRepository;
import com.patika.emlakburada_advertstatus.service.AdvertStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertStatusServiceImpl implements AdvertStatusService {
    private final AdvertRepository advertRepository;


    //Rabbitmq burada gelen statu değişikliğini okuyarak db ye kaydediyor
    @RabbitListener(queues = "${advert.notification.queue}")
    @Override
    public void updateStatus(AdvertStatusRequest request) {

        Optional<Advert> optionalAdvert=advertRepository.findById(request.getId());
        optionalAdvert.get().setAdvertStatus(request.getAdvertStatus());

        advertRepository.save(optionalAdvert.get());

        log.info("notification received and advert status updated on db: {}",request.toString());
    }
}
