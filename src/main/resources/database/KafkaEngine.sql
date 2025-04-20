CREATE TABLE tbl_history_alarm (
    alarm_identifier            String,
    perceived_severity          UInt8,
    detection_time              DateTime,
    manufacturer                String,
    product_class               String,
    ap_serial_number            String,
    event_type                  String,
    event_time                  DateTime,
    category                    String,
    probable_cause              String,
    specific_problem            String,
    addition_text               String,
    addition_information        String,
    notification_type           String,
    manager_object_instance     String,
    pppoe_account               String,
    controller_serial_number    String
) ENGINE = ReplacingMergeTree(event_time)
PARTITION BY toDate(detection_time)
ORDER BY (alarm_identifier, ap_serial_number);

CREATE TABLE tbl_history_alarm_kafka(
    alarm_identifier            String,
    perceived_severity          UInt8,
    detection_time              UInt64, -- dùng UInt64 thay cho DateTime
    manufacturer                String,
    product_class               String,
    ap_serial_number            String,
    event_type                  String,
    event_time                  UInt64, -- dùng UInt64 thay cho DateTime
    category                    String,
    probable_cause              String,
    specific_problem            String,
    addition_text               String,
    addition_information        String,
    notification_type           String,
    manager_object_instance     String,
    pppoe_account               String,
    controller_serial_number    String
) ENGINE = Kafka
      SETTINGS kafka_broker_list = 'kafka:9093',
          kafka_topic_list = 'tbl_history_alarm',
          kafka_group_name = 'alarm-history',
          kafka_format = 'AvroConfluent',
          format_avro_schema_registry_url = 'http://schema-registry:8081';

CREATE MATERIALIZED VIEW tbl_history_alarm_mv TO tbl_history_alarm AS
SELECT
    alarm_identifier,
    perceived_severity,
    toDateTime(detection_time / 1000) AS detection_time,  -- Chia trước khi chuyển DateTime
    manufacturer,
    product_class,
    ap_serial_number,
    event_type,
    toDateTime(event_time / 1000) AS event_time, -- Chia trước khi chuyển DateTime
    category,
    probable_cause,
    specific_problem,
    addition_text,
    addition_information,
    notification_type,
    manager_object_instance,
    pppoe_account,
    controller_serial_number
FROM tbl_history_alarm_kafka;
