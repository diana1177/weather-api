package org.example.model;

public record WeatherData(
        Double latitude,
        Double longitude,
        Double generationtime_ms,
        Integer utc_offset_seconds,
        String timezone,
        String timezone_abbreviation,
        Double elevation,
        CurrentWeatherUnits current_weather_units,
        CurrentWeather current_weather
) {
}
