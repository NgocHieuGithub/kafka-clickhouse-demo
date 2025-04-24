package com.system.kafkaclickhouse.controller;

import com.system.kafkaclickhouse.dto.Alarm;
import com.system.kafkaclickhouse.service.KafkaService;
import com.system.kafkaclickhouse.service.serviceImpl.ExcelExportService;
import com.system.kafkaclickhouse.service.serviceImpl.KafkaAvro;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("alarm")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmController {

//    KafkaService kafkaService;
//
//    @PostMapping
//    public ResponseEntity<?> sendMessage(@RequestBody Alarm request) {
//        kafkaService.sendData(request);
//        return ResponseEntity.ok("Done .......................");
//    }
//
//    // Test performance kafka
//    @PostMapping("/v2")
//    public ResponseEntity<?> sendMessageV2(@RequestBody Alarm request) {
//        kafkaService.sendDataV2(request);
//        return ResponseEntity.ok("Done ........................");
//    }
    KafkaAvro kafkaService;
    private final ExcelExportService excelExportService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> map,@RequestParam String serial) {
        kafkaService.sendKafkaAvro(map, serial);
        return ResponseEntity.ok("Done .......................");
    }

    @GetMapping
    public ResponseEntity<?> tesst() throws SQLException, IOException {
        excelExportService.exportDataToExcel();
        return ResponseEntity.ok("Done .......................");
    }
}
