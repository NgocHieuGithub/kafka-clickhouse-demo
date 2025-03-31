-- Report by product class
CREATE TABLE report_by_productClass(
    detectionTime    DateTime,
    productClass     String,
    count            AggregateFunction(count, UInt32)
)   ENGINE = AggregatingMergeTree()
ORDER BY (detectionTime, productClass);

CREATE MATERIALIZED VIEW report_by_productClass_mv TO report_by_productClass AS
SELECT detectionTime, productClass, countState() AS count
FROM alarm_events
GROUP BY detectionTime, productClass;

-- Report by severity
CREATE TABLE report_by_severity(
   detectionTime    DateTime,
   severity     String,
   count            AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (detectionTime, severity);

CREATE MATERIALIZED VIEW report_by_severity_mv TO report_by_severity AS
SELECT detectionTime, severity, countState() AS count
FROM alarm_events
GROUP BY detectionTime, severity;

-- Report by event type
CREATE TABLE report_by_eventType(
   detectionTime    DateTime,
   eventType     String,
   count            AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (detectionTime, eventType);

CREATE MATERIALIZED VIEW report_by_eventType_mv TO report_by_eventType AS
SELECT detectionTime, eventType, countState() AS count
FROM alarm_events
GROUP BY detectionTime, eventType;

--- Test
CREATE TABLE kafka_del_event
(
    alarmIdentifier Nullable(String)
) ENGINE = Kafka
      SETTINGS kafka_broker_list = 'kafka:9093',
          kafka_topic_list = 'del',
          kafka_group_name = 'del-group',
          kafka_format = 'JSONEachRow';

CREATE MATERIALIZED VIEW mv_alarm_del TO alarm_del
AS SELECT alarmIdentifier,
   FROM kafka_del_event;

CREATE TABLE alarm_del (
  alarmIdentifier String
) ENGINE = MergeTree()
ORDER BY alarmIdentifier;
