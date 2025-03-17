package com.example.weatheralertssender.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;

    public EmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlert(String to, String text) {
        var message = new SimpleMailMessage();
        message.setFrom("alert@example.com");
        message.setTo(to);
        message.setSubject("WEATHER ALERT");
        message.setText(text);
        mailSender.send(message);
    }
}
