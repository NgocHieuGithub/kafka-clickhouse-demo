package com.system.kafkaclickhouse.service;

public interface KafkaService {
    <T> void sendData(T request);

    <T> void sendDataV2(T request);
}

