package com.tigran.oauth.service.email.impl;

import com.tigran.oauth.service.email.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 6:23 PM
 */
@Component
@Log4j2
public class EmailSenderServiceImpl implements EmailSenderService {

    @Qualifier("getJavaMailSender")
    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(final String email, final String title, final String text) {
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
