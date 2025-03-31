package com.system.kafkaclickhouse.controller;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import com.system.kafkaclickhouse.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("alarm")
public class AlarmController {
    private final KafkaService kafkaService;
    public AlarmController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody AlarmDTO request) {
        kafkaService.sendData(request);
        return ResponseEntity.ok("Done");
    }

    @PostMapping("/v2")
    public ResponseEntity<?> sendMessageV2(@RequestBody AlarmDTO request) {
        kafkaService.sendDataV2(request);
        return ResponseEntity.ok("Done");
    }
}
