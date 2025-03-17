package com.system.kafkaclickhouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlarmDTO {
    private String alarmIdentifier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alarmChangeTime;
    private String severity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detectionTime;
    private String manufacturer;
    private String productClass;
    private String serialNumber;
    private String eventType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;
    private String category;
    private String probableCause;
    private String specificProblem;
    private String additionalText;
    private String additionalInformation;
    private String notificationType;
    private String managedObjectInstance;
    private String pppoeAccount;
    private String controllerSerial;
}
