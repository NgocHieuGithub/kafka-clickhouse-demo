package com.system.kafkaclickhouse.service;

public interface AlarmCacheService {
    void processIncomingAlarm(String severity, String eventType);
}
