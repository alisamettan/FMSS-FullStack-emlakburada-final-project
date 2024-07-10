package com.patika.emlakburada_notification_consumer.consumer;

import com.patika.emlakburada_notification_consumer.dto.UserNotificationRequest;
import com.patika.emlakburada_notification_consumer.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailSenderService emailSenderService;

    @RabbitListener(queues = "${user.notification.queue}")
    public void receiveUserNotification(UserNotificationRequest request){
        log.info("Customer notification is sent: {}",request.toString());
        emailSenderService.emailSender(request);
    }
}
