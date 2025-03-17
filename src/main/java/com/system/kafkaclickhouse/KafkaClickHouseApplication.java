package com.system.kafkaclickhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaClickHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaClickHouseApplication.class, args);
        System.out.println("Start system ...........");
    }

}
