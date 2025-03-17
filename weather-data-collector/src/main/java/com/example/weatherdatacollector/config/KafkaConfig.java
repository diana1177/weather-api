package com.example.weatherdatacollector.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.model.WeatherData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, WeatherData> getProducerFactory() {
        Map<String, Object> mapConfig = new HashMap<>();
        mapConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        mapConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        mapConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        JsonSerializer<WeatherData> serializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(mapConfig, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, WeatherData> getKafkaTemplate() {
        return new KafkaTemplate<>(getProducerFactory());
    }
}
