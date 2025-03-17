package com.system.kafkaclickhouse.service.serviceImpl;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import com.system.kafkaclickhouse.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j(topic = "Alarm-service-log")
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    @Override
    public List<AlarmDTO> search(String severity, String detectionTime, String manufacturer,
                                      String productClass, String serialNumber, String eventType) {
        return List.of();
    }
}
