-- ----- Create materialized view filter productClass ------

-- Table to store alarm logs with productClass

CREATE TABLE tbl_vgp42x6v1_productClass_alarm
(
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

CREATE TABLE tbl_vg421wdv2_productClass_alarm
(
    alarmIdentifier       String,
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

CREATE TABLE tbl_vap120wd_productClass_alarm
(
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
-- Create mv to filter productClass

CREATE MATERIALIZED VIEW tbl_vap120wd_productClass_alarm_mv TO tbl_vap120wd_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE product_class = 'vAP-120WD';

CREATE MATERIALIZED VIEW tbl_vg421wdv2_productClass_alarm_mv TO tbl_vg421wdv2_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE product_class = 'vG-421WD-v2';

CREATE MATERIALIZED VIEW tbl_vgp42x6v1_productClass_alarm_mv TO tbl_vgp42x6v1_productClass_alarm AS
SELECT * FROM tbl_history_alarm
WHERE product_class = 'vGP-42X6V1';