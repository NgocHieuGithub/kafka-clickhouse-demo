package com.system.kafkaclickhouse.dao;

import com.system.kafkaclickhouse.dto.Alarm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "AlarmDAO-log")
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmDAO {
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Alarm> search(Integer severity, LocalDateTime detectionTimeStart, LocalDateTime detectionTimeEnd, String manufacturer,
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
        return namedParameterJdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(Alarm.class));
    }

    // Statistic By EventType
    public Map<String, Object> statisticByEventType() {
        StringBuilder sql = new StringBuilder("SELECT event_type, countMerge(count) AS total_count FROM tbl_report_by_eventType GROUP BY event_type");

        MapSqlParameterSource params = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForMap(sql.toString(), params);
    }
    // Statistic By ProductClass
    public Map<String, Object> statisticByProductClass() {
        StringBuilder sql = new StringBuilder("SELECT product_class, countMerge(count) AS total_count FROM tbl_report_by_productClass GROUP BY product_class");

        MapSqlParameterSource params = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForMap(sql.toString(), params);
    }
    // Statistic By Severity
    public Map<String, Object> statisticBySeverity() {
        StringBuilder sql = new StringBuilder("SELECT perceived_severity, countMerge(count) AS total_count FROM tbl_report_by_severity GROUP BY perceived_severity");
        MapSqlParameterSource params = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForMap(sql.toString(), params);
    }

    public Map<String, Object> statisticTotal(){
        return jdbcTemplate.queryForMap("select count() as Total_Alarm, countIf(severity = 'Cleared') as Total_Resolved_Alarm from tbl_history_alarm ae final");
    }

    public Map<String, Object> statisticTotalResolved(){
        return jdbcTemplate.queryForMap("select countIf(severity = 0) as Total_Resolved_Alarm from tbl_history_alarm ae final");
    }

    @Cacheable(value = "config_filter_alarm", cacheManager = "cacheManager")
    public Map<String, List<Integer>> checkFilterAlarm() {
        String sql = "SELECT category, event_type, save_database, call_third_party FROM tbl_config_filter_alarm";
        log.info("Query database .................");
        List<Object[]> results = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Object[] {
                    rs.getString("category"),
                    rs.getString("event_type"),
                    rs.getInt("save_database"),
                    rs.getInt("call_third_party")
            };
        });

        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (Object[] row : results) {
            String key = row[0] + "_" + row[1];  // category + event_type
            List<Integer> value = Arrays.asList((Integer) row[2], (Integer) row[3]);

            resultMap.put(key, value);
        }
        log.info("ResultMap: {}", resultMap);
        return resultMap;
    }
}
