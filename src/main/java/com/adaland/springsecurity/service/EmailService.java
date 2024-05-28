package com.adaland.springsecurity.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:mail.properties"),
        @PropertySource("classpath:mail-messages.properties")
})

@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${username}")
    private String sender;

    @Value("${welcome-subject}")
    private String welcomeSubject;

    @Value("${welcome-message}")
    private String welcomeMessage;

    @Value("${login-url}")
    private String loginUrl;

    public void sendMessage(
            String recipient, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
        log.info("Message sent successfully");

    }

    public void sendWelcomeMessage(
            String recipient, String name, String username) {
        if (name == null) name="";
        SimpleMailMessage mailMessage=prepareWelcomeEmailToSent(recipient, name, username);
        javaMailSender.send(mailMessage);
        log.info("Message sent successfully");
    }

    private SimpleMailMessage prepareWelcomeEmailToSent(String recipient, String name, String username) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(welcomeSubject);
        String message = String.format(welcomeMessage, name, username);
        mailMessage.setText(message);
        return mailMessage;
    }

}
