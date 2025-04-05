package com.system.kafkaclickhouse.controller;

import com.system.kafkaclickhouse.service.AlarmService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("statistic")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticController {
    AlarmService alarmService;

    @GetMapping("/productClass")
    public ResponseEntity<?> statisticProductClass(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer severity){
        return ResponseEntity.ok().body(alarmService.statisticByProductClass(startDate, endDate, severity));
    }

    @GetMapping("/eventType")
    public ResponseEntity<?> statisticEventType(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer severity){
        return ResponseEntity.ok().body(alarmService.statisticByEventType(startDate, endDate, severity));
    }

    @GetMapping("/severity")
    public ResponseEntity<?> statisticSeverity(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer severity){
        return ResponseEntity.ok().body(alarmService.statisticBySeverity(startDate, endDate, severity));
    }

    @GetMapping("/total")
    public ResponseEntity<?> getAllAlarms() {
        return ResponseEntity.ok().body(alarmService.statisticTotal());
    }

    @GetMapping("/total-resolved")
    public ResponseEntity<?> getAllAlarmsResolved() {
        return ResponseEntity.ok().body(alarmService.statisticTotalResolved());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMessage(
            @RequestParam(required = false) Integer severity,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String productClass,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String eventType) {
        return ResponseEntity.ok().body(alarmService.search(severity, startDate, endDate, manufacturer, productClass, serialNumber, eventType));
    }
}
