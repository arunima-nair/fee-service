package com.example.feeservice.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.feeservice.events.FeePaymentEvent;

@Configuration
public class KafkaProducerConfig {

    // Kafka producer configuration for FeePaymentEvent
    @Bean
    public KafkaTemplate<String, FeePaymentEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Producer factory to configure KafkaProducer with FeePaymentEvent serialization
    private ProducerFactory<String, FeePaymentEvent> producerFactory() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Change if necessary
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        producerProps.put("value.serializer", JsonDeserializer.class.getName()); // Use JsonSerializer to serialize FeePaymentEvent
        return new DefaultKafkaProducerFactory<>(producerProps);
    }
}
