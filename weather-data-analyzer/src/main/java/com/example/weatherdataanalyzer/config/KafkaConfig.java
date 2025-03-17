package com.example.weatherdataanalyzer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.model.WeatherAlert;
import org.example.model.WeatherData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, WeatherData> getConsumerFactory() {
        Map<String, Object> mapConfig = new HashMap<>();
        mapConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        mapConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "weather-analyzer");
        mapConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        mapConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        mapConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<WeatherData> deserializer = new JsonDeserializer<>();

        return new DefaultKafkaConsumerFactory<>(mapConfig, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WeatherData> concurrentKafkaListenerContainerFactory() {
        var concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, WeatherData>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(getConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public ProducerFactory<String, WeatherAlert> getProducerFactory() {
        Map<String, Object> mapConfig = new HashMap<>();
        mapConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        mapConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        mapConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        JsonSerializer<WeatherAlert> serializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(mapConfig, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, WeatherAlert> getKafkaTemplate() {
        return new KafkaTemplate<>(getProducerFactory());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new StringJsonMessageConverter();
    }
}
