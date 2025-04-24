package com.system.kafkaclickhouse.service.serviceImpl;

import com.system.kafkaclickhouse.dto.Alarm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Slf4j(topic = "Kafka-service2-log")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaAvro {
    public static class topic {
        static String ALARM = "tbl_history_alarm";
        static String KEY = "new-alarm";
    }
    ZoneOffset gmt7Plus = ZoneOffset.of("+07:00");
    AvroConverterService avroConverterService;
    KafkaTemplate<String, GenericRecord> kafkaTemplate;

    public void sendKafkaAvro(Map<String, String> map, String serial) {
        Alarm alarm = convertAlarm(map, serial);
        log.info("New alarm {}", alarm);
//        kafkaTemplate.send(topic.ALARM, topic.KEY, avroConverterService.convertAlarm(alarm));
    }
    private Alarm convertAlarm(Map<String, String> map, String serial) {
        Alarm alarm = Alarm.builder().serialNumber(serial).build();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "AlarmIdentifier":
                    alarm.setAlarmIdentifier(entry.getValue());
                    break;
                case "PerceivedSeverity":
                    alarm.setSeverity(Integer.valueOf(entry.getValue()));
                    break;
                case "AlarmRaisedTime":
                    OffsetDateTime raisedTime = OffsetDateTime.parse(entry.getValue().trim().replace(" ", "0"));
                    alarm.setDetectionTime(Timestamp.valueOf(raisedTime.withOffsetSameInstant(gmt7Plus).toLocalDateTime()));
                    break;
                case "Manufacturer":
                    alarm.setManufacturer(entry.getValue());
                    break;
                case "ProductClass":
                    alarm.setProductClass(entry.getValue());
                    break;
                case "SerialNumber":
                    alarm.setSerialNumber(entry.getValue());
                    break;
                case "EventTime":
                    OffsetDateTime eventTime = OffsetDateTime.parse(entry.getValue().trim().replace(" ", "0"));
                    alarm.setEventTime(Timestamp.valueOf(eventTime.withOffsetSameInstant(gmt7Plus).toLocalDateTime()));
                    break;
                case "Category":
                    alarm.setCategory(entry.getValue());
                    break;
                case "ProbableCause":
                    alarm.setProbableCause(entry.getValue());
                    break;
                case "SpecificProblem":
                    alarm.setSpecificProblem(entry.getValue());
                    break;
                case "AdditionalText":
                    alarm.setAdditionalText(entry.getValue());
                    break;
                case "AdditionalInformation":
                    alarm.setAdditionalInformation(entry.getValue());
                    break;
                case "NotificationType":
                    alarm.setNotificationType(entry.getValue());
                    break;
                case "ManagedObjectInstance":
                    alarm.setManagedObjectInstance(entry.getValue());
                    break;
                case "PppoeAccount":
                    alarm.setPppoeAccount(entry.getValue());
                    break;
                case "ControllerSerialNumber":
                    alarm.setControllerSerial(entry.getValue());
                    break;
                case "EventType":
                    alarm.setEventType(entry.getValue());
                    break;
                default:
                    break;
            }
        }
        return alarm;
    }
}
