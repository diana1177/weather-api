package org.example.model;

public record CurrentWeather(
        String time,
        Integer interval,
        Double temperature,
        Double windspeed,
        Integer winddirection,
        Integer is_day,
        Integer weathercode
) {
}

