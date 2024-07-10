package com.patika.emlakburada_notification_consumer.service;


import com.patika.emlakburada_notification_consumer.dto.UserNotificationRequest;
import com.patika.emlakburada_notification_consumer.dto.enums.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;



    //Customer'a kayıt olduğunda mail atan method
    public void emailSender(UserNotificationRequest request) {
        if(request.getNotificationType()== NotificationType.EMAIL){
            sendWelcomeEmail(request);
        }
    }


    private void sendWelcomeEmail(UserNotificationRequest request) {
        if (isValidEmail(request.getEmail())) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getEmail());
            message.setSubject("Welcome to Our Real Estate Listing Website");
            String emailText = String.format(
                    "Dear %s,\n\n" +
                            "Welcome to our real estate listing website. If you would like to list properties on the page, " +
                            "you could check and purchase packages on the package page.\n" +
                            "Your listing rights: %d",
                    request.getFullName(), request.getListingRights());
            message.setText(emailText);


            mailSender.send(message);
            log.info("Welcome email sent to: {}", request.getEmail());
        } else {
            log.error("Invalid email address: {}", request.getEmail());
        }
    }




    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

}
