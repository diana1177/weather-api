package com.example.weatheralertssender.service;

import org.example.model.WeatherAlert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final EmailService emailService;

    public AlertService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Value("${alert.recipient.email}")
    private String recipient;

    @KafkaListener(topics = "weather-alerts", groupId = "weather-alerts-sender")
    public void analyzeMetrics(WeatherAlert weatherAlert) {
        emailService.sendAlert(recipient, weatherAlert.message());
    }
}
