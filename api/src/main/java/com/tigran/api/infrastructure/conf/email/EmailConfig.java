package com.tigran.api.infrastructure.conf.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Tigran Melkonyan
 * Date: 11/29/24
 * Time: 7:55 PM
 */
@Configuration
public class EmailConfig {

    private final String host;
    private final Integer port;
    private final String userName;
    private final String password;

    public EmailConfig(
            @Value("${spring.mail.host}") final String host,
            @Value("${spring.mail.port}") final Integer port,
            @Value("${spring.mail.username}") final String userName,
            @Value("${spring.mail.password}") final String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
