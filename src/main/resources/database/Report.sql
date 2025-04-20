-- Report by product class
CREATE TABLE tbl_report_by_productClass(
    perceived_severity  UInt8,
    detection_time      DateTime,
    product_class       String,
    count               AggregateFunction(count, UInt32)
)   ENGINE = AggregatingMergeTree()
ORDER BY (product_class, detection_time, perceived_severity);

CREATE MATERIALIZED VIEW tbl_report_by_productClass_mv TO tbl_report_by_productClass AS
SELECT product_class, detection_time, perceived_severity, countState() AS count
FROM tbl_history_alarm
GROUP BY product_class, detection_time, perceived_severity;

-- Report by severity
CREATE TABLE tbl_report_by_severity(
   detection_time       DateTime,
   perceived_severity   UInt8,
   count                AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (perceived_severity, detection_time);

CREATE MATERIALIZED VIEW tbl_report_by_severity_mv TO tbl_report_by_severity AS
SELECT perceived_severity, detection_time, countState() AS count
FROM tbl_history_alarm
GROUP BY perceived_severity, detection_time;

-- Report by event type
CREATE TABLE tbl_report_by_eventType(
    perceived_severity  UInt8,
    detection_time      DateTime,
    event_type          String,
    count               AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (event_type , detection_time, perceived_severity);

CREATE MATERIALIZED VIEW tbl_report_by_eventType_mv TO tbl_report_by_eventType AS
SELECT event_type, detection_time, perceived_severity, countState() AS count
FROM tbl_history_alarm
GROUP BY event_type, detection_time, perceived_severity;

-- Report by product class V2
-- CREATE TABLE tbl_report_by_productClassV2(
--    severity         UInt8,
--    detectionTime    DateTime,
--    productClass     String,
--    count            AggregateFunction(count, UInt32)
-- )   ENGINE = AggregatingMergeTree()
-- ORDER BY (productClass, detectionTime, severity);
--
-- CREATE MATERIALIZED VIEW tbl_report_by_productClass_mvV2 TO tbl_report_by_productClassV2 AS
-- SELECT ta.productClass, tha.detectionTime, tha.severity, countState() AS count
-- FROM tbl_history_alarm tha left join tbl_ap ta on tha.serialNumber = ta.serialNumber
-- GROUP BY ta.productClass, tha.detectionTime, tha.severity;