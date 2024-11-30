package com.tigran.api.adapter.outbound.notification;

import com.tigran.api.domain.model.notification.reciver.MessageContent;
import com.tigran.api.domain.model.notification.reciver.NotificationReceiverInfo;
import com.tigran.api.domain.port.outbound.device.NotificationSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 6:23 PM
 */
@Component
@Log4j2
public class EmailSenderService implements NotificationSender {

    @Qualifier("getJavaMailSender")
    private final JavaMailSender mailSender;

    public EmailSenderService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(final NotificationReceiverInfo receiverInfo, final MessageContent messageContent) {
        sendEmailNotification(receiverInfo.getEmail(), messageContent.getTitle(), messageContent.getText());
    }

    private void sendEmailNotification(final String email, final String title, final String text) {
        log.info("Preparing email notification sending to email - {}", email);
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setSubject(title);
            message.setTo(email);
            message.setText(text);
            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("Mail sending failed - {}", e.getMessage());
        }
        log.info("Successfully sent email to user with email - {}", email);
    }

}
