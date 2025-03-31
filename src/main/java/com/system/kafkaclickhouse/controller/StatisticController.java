package com.system.kafkaclickhouse.controller;

import com.system.kafkaclickhouse.dao.AlarmDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
//@RequiredArgsConstructor
@RequestMapping("statistic")
public class StatisticController {
    private final AlarmDAO alarmDAO;

    public StatisticController(AlarmDAO alarmDAO) {
        this.alarmDAO = alarmDAO;
    }

    @GetMapping("/productClass")
    public ResponseEntity<?> statisticProductClass(@RequestParam(required = false) LocalDate startDate,
                                          @RequestParam(required = false) LocalDate endDate){
        return ResponseEntity.ok().body(alarmDAO.statisticByProductClass(startDate, endDate));
    }

    @GetMapping("/eventType")
    public ResponseEntity<?> statisticEventType(@RequestParam(required = false) LocalDate startDate,
                                          @RequestParam(required = false) LocalDate endDate){
        return ResponseEntity.ok().body(alarmDAO.statisticByEventType(startDate, endDate));
    }

    @GetMapping("/severity")
    public ResponseEntity<?> statisticSeverity(@RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate){
        return ResponseEntity.ok().body(alarmDAO.statisticBySeverity(startDate, endDate));
    }

    @GetMapping("/total")
    public ResponseEntity<?> getAllAlarms() {
        return ResponseEntity.ok().body(alarmDAO.statisticTotal());
    }
}
