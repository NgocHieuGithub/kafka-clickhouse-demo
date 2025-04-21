package com.system.kafkaclickhouse.service;

import com.system.kafkaclickhouse.dto.Alarm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AlarmService {
    List<Alarm> search(Integer severity, LocalDateTime detectionTimeStart, LocalDateTime detectionTimeEnd, String manufacturer,
                       String productClass, String serialNumber, String eventType);
    Map<String, Object> statisticByEventType();
    Map<String, Object> statisticByProductClass();
    Map<String, Object> statisticBySeverity();
    Map<String, Object> statisticTotal();
    Map<String, Object> statisticTotalResolved();
}
