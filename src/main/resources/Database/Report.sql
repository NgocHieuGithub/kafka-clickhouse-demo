-- Report by product class
CREATE TABLE tbl_report_by_productClass(
    severity         UInt8,
    detectionTime    DateTime,
    productClass     String,
    count            AggregateFunction(count, UInt32)
)   ENGINE = AggregatingMergeTree()
ORDER BY (productClass, detectionTime, severity);

CREATE MATERIALIZED VIEW tbl_report_by_productClass_mv TO tbl_report_by_productClass AS
SELECT productClass, detectionTime, severity, countState() AS count
FROM tbl_history_alarm
GROUP BY productClass, detectionTime, severity;

-- Report by severity
CREATE TABLE tbl_report_by_severity(
   detectionTime    DateTime,
   severity         UInt8,
   count            AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (detectionTime, severity);

CREATE MATERIALIZED VIEW tbl_report_by_severity_mv TO tbl_report_by_severity AS
SELECT detectionTime, severity, countState() AS count
FROM tbl_history_alarm
GROUP BY detectionTime, severity;

-- Report by event type
CREATE TABLE tbl_report_by_eventType(
   severity         UInt8,
   detectionTime    DateTime,
   eventType        String,
   count            AggregateFunction(count, UInt32)
)  ENGINE = AggregatingMergeTree()
ORDER BY (eventType, detectionTime, severity);

CREATE MATERIALIZED VIEW tbl_report_by_eventType_mv TO tbl_report_by_eventType AS
SELECT eventType, detectionTime, severity, countState() AS count
FROM tbl_history_alarm
GROUP BY eventType, detectionTime, severity;

