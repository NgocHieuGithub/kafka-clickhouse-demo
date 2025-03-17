
-- Query data from table report by event product lass
SELECT
    productClass,
    countMerge(count) AS total_count
FROM report_by_productClass
-- WHERE detectionTime BETWEEN '2024-03-01' AND '2024-03-02'
GROUP BY productClass;

SELECT
    detectionTime,
    productClass,
    finalizeAggregation(count) AS total_count
FROM report_by_productClass;

-- Query data from table report by event severity
SELECT
    severity,
    countMerge(count) AS total_count
FROM report_by_severity
WHERE detectionTime BETWEEN '2025-03-14' AND '2025-03-25'
GROUP BY severity;

SELECT
    detectionTime,
    severity,
    finalizeAggregation(count) AS total_count
FROM report_by_severity;

-- Query data from table report by event type
SELECT
    eventType,
    countMerge(count) AS total_count
FROM report_by_eventType
-- WHERE detectionTime BETWEEN '2024-03-01' AND '2024-03-02'
GROUP BY eventType;

SELECT
    detectionTime,
    eventType,
    finalizeAggregation(count) AS total_count
FROM report_by_eventType;

-- select from table filter productClass
select *
from vgp42x6v1_productClass_alarm
final ;

select *
from alarm_events
final ;

SELECT * FROM system.tables WHERE engine = 'Kafka';

SELECT * FROM system.kafka_consumers WHERE table = 'kafka_alarm_events';