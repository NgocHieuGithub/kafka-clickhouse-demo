//package com.system.kafkaclickhouse.config;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${spring.kafka.producer.key-serializer}")
//    private String keySerializer;
//
//    @Value("${spring.kafka.producer.value-serializer}")
//    private String valueSerializer;
//
//    @Value("${spring.kafka.producer.batch-size}")
//    private Integer batchSize;
//
//    @Value("${spring.kafka.producer.linger-ms}")
//    private Integer lingerMs;
//
//    @Value("${spring.kafka.producer.compression-type}")
//    private String compressionType;
//
//    @Value("${spring.kafka.producer.max-in-flight-requests-per-connection}")
//    private Integer maxInFlight;
//
//    @Value("${spring.kafka.producer.acks}")
//    private String acks;
//
//    @Value("${spring.kafka.producer.retries}")
//    private Integer retries;
//
//    @Value("${spring.kafka.producer.buffer-memory}")
//    private Long bufferMemory;
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
//        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
//        configProps.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
//        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
//        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlight);
//        configProps.put(ProducerConfig.ACKS_CONFIG, acks);
//        configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
//        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
