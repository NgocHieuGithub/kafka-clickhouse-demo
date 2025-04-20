package com.system.kafkaclickhouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Alarm {
    String alarmIdentifier;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    Timestamp alarmChangeTime;
    Integer severity;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    Timestamp detectionTime;
    String manufacturer;
    String productClass;
    String serialNumber;
    String eventType;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    Timestamp eventTime;
    String category;
    String probableCause;
    String specificProblem;
    String additionalText;
    String additionalInformation;
    String notificationType;
    String managedObjectInstance;
    String pppoeAccount;
    String controllerSerial;
}
