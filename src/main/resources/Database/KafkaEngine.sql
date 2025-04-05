CREATE TABLE tbl_history_alarm (
        alarmIdentifier String,
        alarmChangeTime DateTime,
        severity UInt8,
        detectionTime DateTime,
        manufacturer String,
        productClass String,
        serialNumber String,
        eventType String,
        eventTime DateTime,
        category String,
        probableCause String,
        specificProblem String,
        additionalText String,
        additionalInformation String,
        notificationType String,
        managedObjectInstance String,
        pppoeAccount String,
        controllerSerial String
) ENGINE = ReplacingMergeTree(eventTime)
    partition by toYYYYMMDD(eventTime)
    ORDER BY alarmIdentifier;

CREATE TABLE tbl_history_alarm_kafka(
        alarmIdentifier String,
        alarmChangeTime DateTime,
        severity UInt8,
        detectionTime DateTime,
        manufacturer String,
        productClass String,
        serialNumber String,
        eventType String,
        eventTime DateTime,
        category String,
        probableCause String,
        specificProblem String,
        additionalText String,
        additionalInformation String,
        notificationType String,
        managedObjectInstance String,
        pppoeAccount String,
        controllerSerial String
) ENGINE = Kafka
      SETTINGS kafka_broker_list = 'kafka:9093',
          kafka_topic_list = 'AlarmEvent',
          kafka_group_name = 'alarm-history-new',
          kafka_format = 'JSONEachRow';

CREATE MATERIALIZED VIEW tbl_history_alarm_mv TO tbl_history_alarm AS SELECT * FROM tbl_history_alarm_kafka;
