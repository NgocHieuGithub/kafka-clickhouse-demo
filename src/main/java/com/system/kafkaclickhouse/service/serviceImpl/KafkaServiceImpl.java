package com.system.kafkaclickhouse.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.kafkaclickhouse.service.KafkaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j(topic = "Kafka-service-log")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaServiceImpl implements KafkaService {
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;
    @Override
    public <T> void sendData(T request) {
        try {
            String TOPIC = "AlarmEvent";
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
    @Override
    public <T> void sendDataV2(T request) {
        try {
            String TOPIC = "alarm_events";
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
