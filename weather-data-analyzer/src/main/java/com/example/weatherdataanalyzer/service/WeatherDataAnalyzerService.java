package com.example.weatherdataanalyzer.service;

import org.example.model.CurrentWeather;
import org.example.model.WeatherAlert;
import org.example.model.WeatherData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherDataAnalyzerService {

    private final KafkaTemplate<String, WeatherAlert> kafkaTemplate;

    public WeatherDataAnalyzerService(KafkaTemplate<String, WeatherAlert> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "weather-raw-data", groupId = "weather-analyzer")
    public void analyzeMetrics(WeatherData weatherData) {
        var windspeed = Optional.of(weatherData.current_weather())
                .map(CurrentWeather::windspeed)
                .orElse(0.0);

        var temperature = Optional.of(weatherData.current_weather())
                .map(CurrentWeather::temperature)
                .orElse(0.0);

        if (windspeed >= 10) {
            kafkaTemplate.send("weather-alerts", new WeatherAlert(LocalDateTime.now(), "WARNING: Wind speed is high: " + windspeed));
        } else if (temperature >= 32) {
            kafkaTemplate.send("weather-alerts", new WeatherAlert(LocalDateTime.now(), "WARNING: Temperature is high: " + temperature));
        }
    }
}
