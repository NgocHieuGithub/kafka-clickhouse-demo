package com.system.kafkaclickhouse.service;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import java.util.List;

public interface AlarmService {
    List<AlarmDTO> search(String severity, String detectionTime, String manufacturer,
                          String productClass, String serialNumber, String eventType);
}
