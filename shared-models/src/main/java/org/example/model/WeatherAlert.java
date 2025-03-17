package org.example.model;

import java.time.LocalDateTime;

public record WeatherAlert(LocalDateTime time, String message) {
}
