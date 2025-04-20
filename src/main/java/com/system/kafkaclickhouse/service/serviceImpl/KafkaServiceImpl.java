//package com.system.kafkaclickhouse.service.serviceImpl;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.system.kafkaclickhouse.dao.AlarmDAO;
//import com.system.kafkaclickhouse.dto.Alarm;
//import com.system.kafkaclickhouse.service.AlarmCacheService;
//import com.system.kafkaclickhouse.service.KafkaService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Slf4j(topic = "Kafka-service-log")
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class KafkaServiceImpl implements KafkaService {
//    KafkaTemplate<String, String> kafkaTemplate;
//    ObjectMapper objectMapper;
//    AlarmDAO alarmDAO;
//    AlarmCacheService alarmCacheService;
//    Integer MAX_POLL = 50;
//    @Override
//    public <T> void sendData(T request) {
//        try {
//            log.info("Start send message alarm to kafka.......................");
//            actionAlarm((Alarm) request);
//            log.info("End send message alarm to kafka.......................");
//        } catch (JsonProcessingException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    private void actionAlarm(Alarm alarm) throws JsonProcessingException {
//        String key = alarm.getCategory() + "_" + alarm.getEventType();
//        log.info("Key: {}", key);
//        if (alarmDAO.checkFilterAlarm().containsKey(key)){
//            List<Integer> resultMap = alarmDAO.checkFilterAlarm().get(key);
//            String TOPIC = "AlarmEvent";
//            switch (resultMap.get(0)) {
//                case 1:
//                    log.info("Case 1 action alarm");
//                    kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(convertToJson(alarm)));
//                    break;
//                case 2:
//                    log.info("Case 2 action alarm");
//                    alarmCacheService.processIncomingAlarm(alarm.getSeverity(), alarm.getEventType());
//                    break;
//                default:
//                    log.info("Case nothing action alarm");
//                    break;
//            }
//            if (resultMap.get(1) == 1) {
//                log.info("Call api third ...........");
//                kafkaTemplate.send("call_third_party", objectMapper.writeValueAsString(convertToJson(alarm)));
//            } else {
//                log.info("No call api third .............");
//            }
//        } else {
//            log.info("Key not found");
//        }
//    }
//
//    private Map<String, Object> convertToJson(Alarm alarm){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Map<String, Object> alarmMap = new HashMap<>();
//        alarmMap.put("alarmIdentifier", alarm.getAlarmIdentifier());
//        alarmMap.put("alarmChangeTime", alarm.getAlarmChangeTime() != null ? sdf.format(alarm.getAlarmChangeTime()) : null);
//        alarmMap.put("detectionTime", alarm.getDetectionTime() != null ? sdf.format(alarm.getDetectionTime()) : null);
//        alarmMap.put("eventTime", alarm.getEventTime() != null ? sdf.format(alarm.getEventTime()) : null);
//        alarmMap.put("severity", alarm.getSeverity());
//        alarmMap.put("manufacturer", alarm.getManufacturer());
//        alarmMap.put("productClass", alarm.getProductClass());
//        alarmMap.put("serialNumber", alarm.getSerialNumber());
//        alarmMap.put("eventType", alarm.getEventType());
//        alarmMap.put("category", alarm.getCategory());
//        alarmMap.put("probableCause", alarm.getProbableCause());
//        alarmMap.put("specificProblem", alarm.getSpecificProblem());
//        alarmMap.put("additionalText", alarm.getAdditionalText());
//        alarmMap.put("additionalInformation", alarm.getAdditionalInformation());
//        alarmMap.put("notificationType", alarm.getNotificationType());
//        alarmMap.put("managedObjectInstance", alarm.getManagedObjectInstance());
//        alarmMap.put("pppoeAccount", alarm.getPppoeAccount());
//        alarmMap.put("controllerSerial", alarm.getControllerSerial());
//        return alarmMap;
//    }
//
//    @CacheEvict(value = "config_filter_alarm", allEntries = true)
//    @Scheduled(fixedRate = 120000) // 2p
//    public void clearCache() {
//        System.out.println("Cache 'config_filter_alarm' has been cleared.");
//    }
//
//    List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
//    @Override
//    @KafkaListener(topics = "call_third_party", groupId = "call_third_party_group", containerFactory = "batchFactory")
//    public void consumeBatch(List<ConsumerRecord<String, String>> records) {
//        buffer.addAll(records);
//        if (buffer.size() >= MAX_POLL) {
//            log.info("Send {} messages to api third party", MAX_POLL);
//            // Gọi API xử lý tại đây
//            buffer.subList(0, MAX_POLL).clear(); // Remove first 50 after processing
//        }
//    }
//
//}
