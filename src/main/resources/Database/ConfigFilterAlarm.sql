-- Tạo bảng config_filter_alarm
CREATE TABLE tbl_config_filter_alarm
(
    id UInt64,
    category String,
    event_type String,
    save_database UInt8,
    call_third_party UInt8
) ENGINE = MergeTree() ORDER BY id;

-- Tạo bảng alarm_counting
CREATE TABLE tbl_alarm_counting
(
    id UInt64,
    severity String,
    event_type String,
    number UInt32,
    version UInt32
) ENGINE = MergeTree() ORDER BY id;
