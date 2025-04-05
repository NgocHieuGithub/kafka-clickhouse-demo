package com.system.kafkaclickhouse.controller;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import com.system.kafkaclickhouse.service.KafkaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("alarm")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmController {

    KafkaService kafkaService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody AlarmDTO request) {
        kafkaService.sendData(request);
        return ResponseEntity.ok("Done .......................");
    }

    // Test performance kafka
    @PostMapping("/v2")
    public ResponseEntity<?> sendMessageV2(@RequestBody AlarmDTO request) {
        kafkaService.sendDataV2(request);
        return ResponseEntity.ok("Done ........................");
    }

}
