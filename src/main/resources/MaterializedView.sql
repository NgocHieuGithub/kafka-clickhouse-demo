CREATE TABLE alarm_events (
                              alarmIdentifier String,
                              alarmChangeTime Nullable(DateTime64(3)),
                              severity Nullable(String),
                              detectionTime DateTime64(3),
                              manufacturer Nullable(String),
                              productClass Nullable(String),
                              serialNumber Nullable(String),
                              eventType Nullable(String),
                              eventTime DateTime64(3),
                              category Nullable(String),
                              probableCause Nullable(String),
                              specificProblem Nullable(String),
                              additionalText Nullable(String),
                              additionalInformation Nullable(String),
                              notificationType Nullable(String),
                              managedObjectInstance Nullable(String),
                              pppoeAccount Nullable(String),
                              controllerSerial Nullable(String)
) ENGINE = ReplacingMergeTree(eventTime)
    ORDER BY alarmIdentifier;

CREATE TABLE kafka_alarm_events
(
    alarmIdentifier Nullable(String),
    alarmChangeTime Nullable(DateTime64(3)),
    severity Nullable(String),
    detectionTime DateTime64(3),
    manufacturer Nullable(String),
    productClass Nullable(String),
    serialNumber Nullable(String),
    eventType Nullable(String),
    eventTime DateTime64(3),
    category Nullable(String),
    probableCause Nullable(String),
    specificProblem Nullable(String),
    additionalText Nullable(String),
    additionalInformation Nullable(String),
    notificationType Nullable(String),
    managedObjectInstance Nullable(String),
    pppoeAccount Nullable(String),
    controllerSerial Nullable(String)
) ENGINE = Kafka
      SETTINGS kafka_broker_list = 'kafka:9093',
          kafka_topic_list = 'AlarmEvent',
          kafka_group_name = 'alarm-group',
          kafka_format = 'JSONEachRow';

CREATE MATERIALIZED VIEW mv_alarm_events
            TO alarm_events
AS SELECT
              alarmIdentifier,
              alarmChangeTime,
              severity,
              detectionTime,
              manufacturer,
              productClass,
              serialNumber,
              eventType,
              eventTime,
              category,
              probableCause,
              specificProblem,
              additionalText,
              additionalInformation,
              notificationType,
              managedObjectInstance,
              pppoeAccount,
              controllerSerial
   FROM kafka_alarm_events;

-- ----- Create materialized view filter severity, eventType, productClass ------

-- Table to store alarm logs with severity Cleared
CREATE TABLE cleared_severity_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
       ORDER BY alarmIdentifier;

CREATE MATERIALIZED VIEW cleared_severity_alarm_mv
     TO cleared_severity_alarm AS
SELECT *
FROM alarm_events
WHERE severity = 'Cleared';

-- Table to store alarm logs with severity Indeterminate
CREATE TABLE indeterminate_severity_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

CREATE MATERIALIZED VIEW indeterminate_severity_alarm_mv
     TO indeterminate_severity_alarm AS
SELECT *
FROM alarm_events
WHERE severity = 'Indeterminate';

-- Table to store alarm logs with severity Warning
CREATE TABLE warning_severity_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

CREATE MATERIALIZED VIEW warning_severity_alarm_mv
     TO warning_severity_alarm AS
SELECT *
FROM alarm_events
WHERE severity = 'Warning';

-- Table to store alarm logs with eventType

-- Bảng cho eventType = 'mesh.04'
CREATE TABLE mesh_04_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'mesh.06'
CREATE TABLE mesh_06_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'sta.01'
CREATE TABLE sta_01_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'sta.06'
CREATE TABLE sta_06_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'eth.01'
CREATE TABLE eth_01_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'eth.02'
CREATE TABLE eth_02_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'sta.02'
CREATE TABLE sta_02_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'sta.14'
CREATE TABLE sta_14_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Bảng cho eventType = 'sta.05'
CREATE TABLE sta_05_eventType_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Create mv to filter eventType

CREATE MATERIALIZED VIEW mesh_04_eventType_alarm_mv
     TO mesh_04_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'mesh.04';

CREATE MATERIALIZED VIEW mesh_06_eventType_alarm_mv
     TO mesh_06_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'mesh.06';

CREATE MATERIALIZED VIEW sta_01_eventType_alarm_mv
     TO sta_01_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'sta.01';

CREATE MATERIALIZED VIEW sta_06_eventType_alarm_mv
     TO sta_06_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'sta.06';

CREATE MATERIALIZED VIEW eth_01_eventType_alarm_mv
     TO eth_01_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'eth.01';

CREATE MATERIALIZED VIEW eth_02_eventType_alarm_mv
     TO eth_02_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'eth.02';

CREATE MATERIALIZED VIEW sta_02_eventType_alarm_mv
     TO sta_02_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'sta.02';

CREATE MATERIALIZED VIEW sta_14_eventType_alarm_mv
     TO sta_14_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'sta.14';

CREATE MATERIALIZED VIEW sta_05_eventType_alarm_mv
     TO sta_05_eventType_alarm AS
SELECT *
FROM alarm_events
WHERE eventType = 'sta.05';

-- Table to store alarm logs with productClass

CREATE TABLE vgp42x6v1_productClass_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

CREATE TABLE vg421wdv2_productClass_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

CREATE TABLE vap120wd_productClass_alarm
(
    alarmIdentifier       String,
    alarmChangeTime       DateTime,
    severity              String,
    detectionTime         DateTime,
    manufacturer          String,
    productClass          String,
    serialNumber          String,
    eventType             String,
    eventTime             DateTime,
    category              String,
    probableCause         String,
    specificProblem       String,
    additionalText        String,
    additionalInformation String,
    notificationType      String,
    managedObjectInstance String,
    pppoeAccount          String,
    controllerSerial      String
) ENGINE = ReplacingMergeTree(eventTime)
      ORDER BY alarmIdentifier;

-- Create mv to filter productClass

CREATE MATERIALIZED VIEW vap120wd_productClass_alarm_mv
             TO vap120wd_productClass_alarm AS
SELECT *
FROM alarm_events
WHERE productClass = 'vAP-120WD';

CREATE MATERIALIZED VIEW vg421wdv2_productClass_alarm_mv
             TO vg421wdv2_productClass_alarm AS
SELECT *
FROM alarm_events
WHERE productClass = 'vG-421WD-v2';
--
CREATE MATERIALIZED VIEW vgp42x6v1_productClass_alarm_mv
             TO vgp42x6v1_productClass_alarm AS
SELECT *
FROM alarm_events
WHERE productClass = 'vGP-42X6V1';