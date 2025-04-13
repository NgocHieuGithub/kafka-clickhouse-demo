package com.system.kafkaclickhouse.service.serviceImpl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.system.kafkaclickhouse.dao.AlarmCountingDAO;
import com.system.kafkaclickhouse.service.AlarmCacheService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlarmCacheServiceImpl implements AlarmCacheService {

    private static final Logger log = LoggerFactory.getLogger(AlarmCacheServiceImpl.class);
    // Map key: kết hợp của severity và eventType (ví dụ: "CRITICAL:ALERT")
    // Value: số đếm alarm hiện có
    ConcurrentHashMap<String, AtomicInteger> alarmCache = new ConcurrentHashMap<>();

    // Ngưỡng đẩy xuống DB (ví dụ 50 bản tin)
    private static final int THRESHOLD = 2;
    AlarmCountingDAO alarmCountingDAO;

    public AlarmCacheServiceImpl(AlarmCountingDAO alarmCountingDAO) {
        this.alarmCountingDAO = alarmCountingDAO;
    }

    /**
     * Gọi phương thức này mỗi khi có alarm đến.
     * @param severity Mức độ của alarm (ví dụ: CRITICAL, WARNING, ...)
     * @param eventType Loại alarm (ví dụ: ALERT, FAULT, ...)
     */
    @Override
    public void processIncomingAlarm(String severity, String eventType) {
        String key = generateKey(severity, eventType);
        // Nếu chưa tồn tại key, thêm mới với giá trị 0
        alarmCache.putIfAbsent(key, new AtomicInteger(0));

        // Tăng giá trị counter
        int count = alarmCache.get(key).incrementAndGet();

        // Nếu đạt ngưỡng, đẩy xuống DB và reset giá trị
        if (count >= THRESHOLD) {
            flushAlarmCount(key, count);
            alarmCache.get(key).set(0);
        }
    }

    private String generateKey(String severity, String eventType) {
        return severity + ":" + eventType;
    }

    /**
     * Phương thức đẩy giá trị từ cache xuống DB.
     * Có thể sử dụng repository để cập nhật/upsert trong DB.
     * Phương thức này có thể đặt @Transactional nếu cần đảm bảo atomic update.
     */
    @Transactional
    public void flushAlarmCount(String key, int count) {
        // Tách key để lấy severity và event_type
        String[] parts = key.split(":");
        String severity = parts[0];
        String eventType = parts[1];

        // Ví dụ: gọi repository để cập nhật hoặc insert vào bảng alarm_counting
        alarmCountingDAO.insertOrUpdateAlarmCounting(Integer.valueOf(severity), eventType, count);
        System.out.println("Flushing key: " + key + " with count: " + count);
    }

    /**
     * Thực hiện flush định kỳ (mỗi 5 giây chẳng hạn) các bản ghi chưa đạt ngưỡng
     */
    @Scheduled(fixedRate = 120000)
    public void flushCachePeriodically() {
        log.info("Sync flush cache periodically");
        for (String key : alarmCache.keySet()) {
            int count = alarmCache.get(key).get();
            if (count > 0 && count < THRESHOLD) {
                flushAlarmCount(key, count);
                alarmCache.get(key).set(0);
            }
        }
    }
}

