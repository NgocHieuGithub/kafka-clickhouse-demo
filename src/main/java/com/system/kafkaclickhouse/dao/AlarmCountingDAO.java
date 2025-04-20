package com.system.kafkaclickhouse.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "AlarmCounting-log")
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmCountingDAO {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertOrUpdateAlarmCounting(Integer severity, String eventType, String productClass, Integer count) {
        String sql = """
            INSERT INTO tbl_alarm_counting (product_class, perceived_severity, event_type, number, version)
            SELECT :productClass, :severity, :eventType, sum(number) + :count, max(version) + 1
            FROM tbl_alarm_counting
            WHERE severity = :severity AND event_type = :eventType AND product_class = :productClass
            GROUP BY perceived_severity, event_type, product_class
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("severity", severity)
                .addValue("eventType", eventType)
                .addValue("count", count);

        int rows = namedParameterJdbcTemplate.update(sql, params);
        if (rows == 0) {
            // Nếu không có bản ghi, thực hiện insert lần đầu tiên
            String insertSql = """
                INSERT INTO tbl_alarm_counting (product_class, perceived_severity, event_type, number, version)
                VALUES (:productClass, :severity, :eventType, :count, 1)
                """;
            namedParameterJdbcTemplate.update(insertSql, params);
        }

        log.info("Alarm counting updated for severity={}, eventType={}, count={}", severity, eventType, count);
    }
}
