package com.example.weatherdatacollector.client;

import org.example.model.WeatherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-meteo-client", url = "${client.open-meteo.url}")
public interface OpenMeteoClient {

    @GetMapping("/v1/forecast")
    WeatherData getWeatherData(@RequestParam String latitude, @RequestParam String longitude, @RequestParam Boolean current_weather);
}
