-- ----- Create materialized view filter productClass ------

-- Table to store alarm logs with productClass

CREATE TABLE tbl_vgp42x6v1_productClass_alarm
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
) ENGINE = ReplacingMergeTree(eventTime) partition by toYYYYMMDD(eventTime) ORDER BY alarmIdentifier;

CREATE TABLE tbl_vg421wdv2_productClass_alarm
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
) ENGINE = ReplacingMergeTree(eventTime) partition by toYYYYMMDD(eventTime) ORDER BY alarmIdentifier;

CREATE TABLE tbl_vap120wd_productClass_alarm
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
) ENGINE = ReplacingMergeTree(eventTime) partition by toYYYYMMDD(eventTime) ORDER BY alarmIdentifier;

-- Create mv to filter productClass

CREATE MATERIALIZED VIEW tbl_vap120wd_productClass_alarm_mv TO tbl_vap120wd_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE productClass = 'vAP-120WD';

CREATE MATERIALIZED VIEW tbl_vg421wdv2_productClass_alarm_mv TO tbl_vg421wdv2_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE productClass = 'vG-421WD-v2';

CREATE MATERIALIZED VIEW tbl_vgp42x6v1_productClass_alarm_mv TO tbl_vgp42x6v1_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE productClass = 'vGP-42X6V1';