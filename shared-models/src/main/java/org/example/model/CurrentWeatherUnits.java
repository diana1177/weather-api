package org.example.model;

public record CurrentWeatherUnits(
        String time,
        String interval,
        String temperature,
        String windspeed,
        String winddirection,
        String is_day,
        String weathercode
) {
}
