package com.system.kafkaclickhouse.dao;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
//@RequiredArgsConstructor
public class AlarmDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AlarmDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<AlarmDTO> search(String severity, String detectionTime, String manufacturer,
                                 String productClass, String serialNumber, String eventType){
        StringBuilder sql = new StringBuilder("SELECT * FROM alarm_events FINAL WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (severity != null) {
            sql.append(" AND severity = ?");
            params.add(severity);
        }
        if (detectionTime != null) {
            sql.append(" AND detectionTime >= ?");
            params.add(Timestamp.valueOf(detectionTime));
        }
        if (manufacturer != null) {
            sql.append(" AND manufacturer = ?");
            params.add(manufacturer);
        }
        if (productClass != null) {
            sql.append(" AND productClass = ?");
            params.add(productClass);
        }
        if (serialNumber != null) {
            sql.append(" AND serialNumber = ?");
            params.add(serialNumber);
        }
        if (eventType != null) {
            sql.append(" AND eventType = ?");
            params.add(eventType);
        }
        sql.append(" ORDER BY detectionTime DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<AlarmDTO>(AlarmDTO.class));
    }

    public List<AlarmDTO> searchV2(String severity, String detectionTime, String manufacturer,
                                 String productClass, String serialNumber, String eventType){
        StringBuilder sql = new StringBuilder("SELECT * FROM alarm_events FINAL WHERE 1=1");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (severity != null) {
            sql.append(" AND severity = :severity");
            params.addValue("severity", severity);
        }
        if (detectionTime != null) {
            sql.append(" AND detectionTime >= :detectionTime");
            params.addValue("detectionTime", Timestamp.valueOf(detectionTime));
        }
        if (manufacturer != null) {
            sql.append(" AND manufacturer = :manufacturer");
            params.addValue("manufacturer", manufacturer);
        }
        if (productClass != null) {
            sql.append(" AND productClass = :productClass");
            params.addValue("productClass", productClass);
        }
        if (serialNumber != null) {
            sql.append(" AND serialNumber = :serialNumber");
            params.addValue("serialNumber", serialNumber);
        }
        if (eventType != null) {
            sql.append(" AND eventType = :eventType");
            params.addValue("eventType", eventType);
        }
        sql.append(" ORDER BY detectionTime DESC ");
        return namedParameterJdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(AlarmDTO.class));
    }
    // Statistic By EventType
    public List<Map<String, Object>> statisticByEventType(LocalDate startTime, LocalDate endTime) {
        StringBuilder sql = new StringBuilder("SELECT eventType, countMerge(count) AS total_count FROM report_by_eventType");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }

        sql.append(" GROUP BY eventType");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }
    // Statistic By ProductClass
    public List<Map<String, Object>> statisticByProductClass(LocalDate startTime, LocalDate endTime) {
        StringBuilder sql = new StringBuilder("SELECT productClass, countMerge(count) AS total_count FROM report_by_productClass");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }

        sql.append(" GROUP BY productClass");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }
    // Statistic By Severity
    public List<Map<String, Object>> statisticBySeverity(LocalDate startTime, LocalDate endTime) {
        StringBuilder sql = new StringBuilder("SELECT severity, countMerge(count) AS total_count FROM report_by_severity");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }

        sql.append(" GROUP BY severity");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }
    public Map<String, Object> statisticTotal(){
        StringBuilder sql = new StringBuilder("select count() as Total_Alarm, countIf(severity = 'Cleared') as Total_Resolved_Alarm from alarm_events ae final");
        return jdbcTemplate.queryForMap(sql.toString());
    }

}
