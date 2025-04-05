package com.system.kafkaclickhouse.dao;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "AlarmDAO-log")
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmDAO {
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<AlarmDTO> search(Integer severity, LocalDateTime detectionTimeStart, LocalDateTime detectionTimeEnd, String manufacturer,
                                 String productClass, String serialNumber, String eventType){
        StringBuilder sql = new StringBuilder("SELECT * ");

        MapSqlParameterSource params = new MapSqlParameterSource();
        if (productClass != null){
            switch(productClass) {
                case "vAP-120WD":
                    sql.append(" From tbl_vap120wd_productClass_alarm Where 1 = 1");
                    break;
                case "vG-421WD-v2":
                    sql.append(" From tbl_vg421wdv2_productClass_alarm Where 1 = 1");
                    break;
                case "vGP-42X6V1":
                    sql.append(" From tbl_vgp42x6v1_productClass_alarm Where 1 = 1");
                    break;
                default:
                    sql.append(" From tbl_history_alarm Where 1 = 1");
                    break;
            }
            params.addValue("productClass", productClass);
        }
        if (severity != null) {
            sql.append(" AND severity = :severity");
            params.addValue("severity", severity);
        }
        if (detectionTimeStart != null && detectionTimeEnd != null) {
            sql.append(" AND detectionTime between :detectionTimeStart and :detectionTimeEnd");
            params.addValue("detectionTimeStart", detectionTimeStart);
            params.addValue("detectionTimeEnd", detectionTimeEnd);
        }
        if (manufacturer != null) {
            sql.append(" AND manufacturer = :manufacturer");
            params.addValue("manufacturer", manufacturer);
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
    public List<Map<String, Object>> statisticByEventType(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        StringBuilder sql = new StringBuilder("SELECT eventType, countMerge(count) AS total_count FROM tbl_report_by_eventType");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }
        if (severity != null){
            sql.append(" AND severity = :severity");
            params.addValue("severity", severity);
        }
        sql.append(" GROUP BY eventType");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }
    // Statistic By ProductClass
    public List<Map<String, Object>> statisticByProductClass(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        StringBuilder sql = new StringBuilder("SELECT productClass, countMerge(count) AS total_count FROM tbl_report_by_productClass");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }
        if (severity != null){
            sql.append(" AND severity = :severity");
            params.addValue("severity", severity);
        }
        sql.append(" GROUP BY productClass");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }
    // Statistic By Severity
    public List<Map<String, Object>> statisticBySeverity(LocalDateTime startTime, LocalDateTime endTime, Integer severity) {
        StringBuilder sql = new StringBuilder("SELECT severity, countMerge(count) AS total_count FROM tbl_report_by_severity");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (startTime != null && endTime != null) {
            sql.append(" WHERE detectionTime BETWEEN :startTime AND :endTime");
            params.addValue("startTime", startTime);
            params.addValue("endTime", endTime);
        }
        if (severity != null){
            sql.append(" AND severity = :severity");
            params.addValue("severity", severity);
        }
        sql.append(" GROUP BY severity");

        return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
    }

    public Map<String, Object> statisticTotal(){
        return jdbcTemplate.queryForMap("select count() as Total_Alarm, countIf(severity = 'Cleared') as Total_Resolved_Alarm from tbl_history_alarm ae final");
    }

    public Map<String, Object> statisticTotalResolved(){
        return jdbcTemplate.queryForMap("select countIf(severity = 0) as Total_Resolved_Alarm from tbl_history_alarm ae final");
    }
}
