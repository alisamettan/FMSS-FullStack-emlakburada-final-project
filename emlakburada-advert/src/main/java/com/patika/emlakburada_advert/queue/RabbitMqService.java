package com.patika.emlakburada_advert.queue;



import com.patika.emlakburada_advert.config.RabbitConfig;
import com.patika.emlakburada_advert.dto.request.AdvertStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqService {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitConfig rabbitConfig;

    public void sendNotification(AdvertStatusRequest request){
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(),rabbitConfig.getRoutingKey(),request);
        log.info("notification sent : {}",rabbitConfig.getExchange());

    }
}