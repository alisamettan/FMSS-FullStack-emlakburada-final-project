package com.patika.emlakburada_user.queue;




import com.patika.emlakburada_user.config.RabbitConfig;
import com.patika.emlakburada_user.dto.request.UserNotificationRequest;
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

    public void sendNotification(UserNotificationRequest request){
        rabbitTemplate.convertAndSend(rabbitConfig.getExchange(),rabbitConfig.getRoutingKey(),request);
        log.info("notification sent : {}",rabbitConfig.getExchange());

    }
}