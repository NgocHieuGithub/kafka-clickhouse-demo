package com.system.kafkaclickhouse.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.kafkaclickhouse.dto.AlarmDTO;
import com.system.kafkaclickhouse.service.KafkaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
            log.info("Start send message alarm to kafka.......................");
            String TOPIC = "AlarmEvent";
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(convertToJson((AlarmDTO) request)));
            log.info("End send message alarm to kafka.......................");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
    private Map<String, Object> convertToJson(AlarmDTO alarm){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Map<String, Object> alarmMap = new HashMap<>();
        alarmMap.put("alarmIdentifier", alarm.getAlarmIdentifier());
        alarmMap.put("alarmChangeTime", alarm.getAlarmChangeTime() != null ? sdf.format(alarm.getAlarmChangeTime()) : null);
        alarmMap.put("detectionTime", alarm.getDetectionTime() != null ? sdf.format(alarm.getDetectionTime()) : null);
        alarmMap.put("eventTime", alarm.getEventTime() != null ? sdf.format(alarm.getEventTime()) : null);
        alarmMap.put("severity", alarm.getSeverity());
        alarmMap.put("manufacturer", alarm.getManufacturer());
        alarmMap.put("productClass", alarm.getProductClass());
        alarmMap.put("serialNumber", alarm.getSerialNumber());
        alarmMap.put("eventType", alarm.getEventType());
        alarmMap.put("category", alarm.getCategory());
        alarmMap.put("probableCause", alarm.getProbableCause());
        alarmMap.put("specificProblem", alarm.getSpecificProblem());
        alarmMap.put("additionalText", alarm.getAdditionalText());
        alarmMap.put("additionalInformation", alarm.getAdditionalInformation());
        alarmMap.put("notificationType", alarm.getNotificationType());
        alarmMap.put("managedObjectInstance", alarm.getManagedObjectInstance());
        alarmMap.put("pppoeAccount", alarm.getPppoeAccount());
        alarmMap.put("controllerSerial", alarm.getControllerSerial());
        return alarmMap;
    }

    // Test performance kafka
    @Override
    public <T> void sendDataV2(T request) {
        try {
//            String TOPIC = "alarm_events";
            kafkaTemplate.send("test2", objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
