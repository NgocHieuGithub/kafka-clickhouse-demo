package com.system.kafkaclickhouse.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

public interface KafkaService {
    <T> void sendData(T request);

    void consumeBatch(List<ConsumerRecord<String, String>> records);

    <T> void sendDataV2(T request);
}

