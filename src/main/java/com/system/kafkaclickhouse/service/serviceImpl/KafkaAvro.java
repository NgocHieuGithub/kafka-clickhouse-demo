package com.system.kafkaclickhouse.service.serviceImpl;

import com.system.kafkaclickhouse.dto.Alarm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j(topic = "Kafka-service2-log")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaAvro {
    public static class topic {
        static String ALARM = "tbl_history_alarm";
        static String KEY = "new-alarm";
    }
    AvroConverterService avroConverterService;
    KafkaTemplate<String, GenericRecord> kafkaTemplate;

    public void sendKafkaAvro(Alarm alarm) {
        kafkaTemplate.send(topic.ALARM, topic.KEY, avroConverterService.convertAlarm(alarm));
    }
}
