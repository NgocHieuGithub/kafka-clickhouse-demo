package com.system.kafkaclickhouse.dao;

import com.system.kafkaclickhouse.dto.AlarmDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlarmDAO {
    private final JdbcTemplate jdbcTemplate;

    public AlarmDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
