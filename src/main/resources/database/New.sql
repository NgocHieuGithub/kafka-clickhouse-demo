create table tbl_ap(
    id                          UInt8,
    manufacturer                String,
    product_class               String,
    ap_serial_number            String,
    pppoe_account               String,
    controller_serial_number    String
) engine = MergeTree()
order by (id)

SELECT
    formatDateTime(detection_time, '%m%Y') AS `group`,
    COUNTIf(perceived_severity = 0) AS "0",
    COUNTIf(perceived_severity = 1) AS "1",
    COUNTIf(perceived_severity = 2) AS "2",
    COUNTIf(perceived_severity = 3) AS "3",
    COUNTIf(perceived_severity = 4) AS "4",
    COUNTIf(perceived_severity = 5) AS "5"
FROM tbl_report_by_severity
GROUP BY `group`
UNION ALL
SELECT
    'Total' AS `group`,
    COUNTIf(perceived_severity = 0) AS "0",
    COUNTIf(perceived_severity = 1) AS "1",
    COUNTIf(perceived_severity = 2) AS "2",
    COUNTIf(perceived_severity = 3) AS "3",
    COUNTIf(perceived_severity = 4) AS "4",
    COUNTIf(perceived_severity = 5) AS "5"
FROM tbl_report_by_severity
ORDER BY `group`;