package com.example.weatherdatacollector.service;

import com.example.weatherdatacollector.client.OpenMeteoClient;
import org.example.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherDataCollectorService {

    private final OpenMeteoClient openMeteoClient;

    private final KafkaTemplate<String, WeatherData> kafkaTemplate;

    @Value("${warsaw.latitude}")
    private String latitude;

    @Value("${warsaw.longitude}")
    private String longitude;

    public WeatherDataCollectorService(OpenMeteoClient openMeteoClient, KafkaTemplate<String, WeatherData> kafkaTemplate) {
        this.openMeteoClient = openMeteoClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 60000) //3600000
    public void performTask() {
        var weatherData = openMeteoClient.getWeatherData(latitude, longitude, true);
        kafkaTemplate.send("weather-raw-data", weatherData);
    }
}
