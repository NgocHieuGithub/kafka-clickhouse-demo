package com.system.kafkaclickhouse.service;

import com.system.kafkaclickhouse.dto.AlarmDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AlarmService {
    List<AlarmDTO> search(Integer severity, LocalDateTime detectionTimeStart, LocalDateTime detectionTimeEnd, String manufacturer,
                          String productClass, String serialNumber, String eventType);
    List<Map<String, Object>> statisticByEventType(LocalDateTime startTime, LocalDateTime endTime, Integer severity);
    List<Map<String, Object>> statisticByProductClass(LocalDateTime startTime, LocalDateTime endTime, Integer severity);
    List<Map<String, Object>> statisticBySeverity(LocalDateTime startTime, LocalDateTime endTime, Integer severity);
    Map<String, Object> statisticTotal();
    Map<String, Object> statisticTotalResolved();
}
