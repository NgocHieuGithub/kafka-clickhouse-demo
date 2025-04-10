package com.system.kafkaclickhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaClickHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaClickHouseApplication.class, args);
        System.out.println("Start project name: Kafka-clickhouse-application ...........");
    }

}
