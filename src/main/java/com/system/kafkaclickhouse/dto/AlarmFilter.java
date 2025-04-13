package com.system.kafkaclickhouse.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlarmFilter {
    String apSerialNumber;
    Integer pageSize;
    Integer indexPage;
    List<String> manufactures;
    List<String> productClasses;
    List<String> serialNumbers;
    List<String> severities;
    List<String> eventTypes;
    List<String> categoryTypes;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    LocalDateTime detectionFrom;
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    LocalDateTime detectionTo;
    String sortBy;
    Integer templateId;
}
