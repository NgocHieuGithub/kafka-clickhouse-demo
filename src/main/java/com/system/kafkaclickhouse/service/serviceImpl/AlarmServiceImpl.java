package com.system.kafkaclickhouse.service.serviceImpl;

import com.system.kafkaclickhouse.dao.AlarmDAO;
import com.system.kafkaclickhouse.dto.AlarmDTO;
import com.system.kafkaclickhouse.service.AlarmService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "Alarm-service-log")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmServiceImpl implements AlarmService {

    AlarmDAO alarmDAO;

    @Override
    public List<AlarmDTO> search(Integer severity, LocalDateTime detectionTimeStart, LocalDateTime detectionTimeEnd, String manufacturer,
                                 String productClass, String serialNumber, String eventType) {
        log.info("Search alarm .................");
        return alarmDAO.search(severity, detectionTimeStart, detectionTimeEnd, manufacturer, productClass, serialNumber, eventType);
    }

    @Override
    public List<Map<String, Object>> statisticByEventType(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        log.info("Statistic by event type .................");
        return alarmDAO.statisticByEventType(startTime, endTime, severity);
    }

    @Override
    public List<Map<String, Object>> statisticByProductClass(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        log.info("Statistic by product class .................");
        return alarmDAO.statisticByProductClass(startTime, endTime, severity);
    }

    @Override
    public List<Map<String, Object>> statisticBySeverity(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        log.info("Statistic by severity .................");
        return alarmDAO.statisticBySeverity(startTime, endTime, severity);
    }

    @Override
    public Map<String, Object> statisticTotal() {
        log.info("Statistic total alarm .................");
        return alarmDAO.statisticTotal();
    }

    @Override
    public Map<String, Object> statisticTotalResolved() {
        log.info("Statistic total resolved alarm .................");
        return alarmDAO.statisticTotalResolved();
    }
}
