package com.system.kafkaclickhouse.service.serviceImpl;

import com.system.kafkaclickhouse.dto.Alarm;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Service
public class AvroConverterService {
    private final Schema alarmSchema;
    public AvroConverterService() {
        String alarmSchemaFile = "avro-schema/alarm.avsc";
        try {
            alarmSchema = loadSchema(alarmSchemaFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Schema loadSchema(String alarmSchemaFile) throws IOException {
        ClassLoader classLoader = AvroConverterService.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(alarmSchemaFile)) {
            if (inputStream == null) {
                throw new FileNotFoundException(alarmSchemaFile);
            }
            String schemaStr = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return new Schema.Parser().parse(schemaStr);
        }
    }

    public GenericRecord convertAlarm(Alarm alarm) {
        return new GRecord(alarmSchema)
                .putParam("ap_serial_number", alarm.getSerialNumber())
                .putParam("alarm_identifier", alarm.getAlarmIdentifier())
                .putParam("event_type", alarm.getEventType())
                .putParam("probable_cause", alarm.getProbableCause())
                .putParam("specific_problem", alarm.getSpecificProblem())
                .putParam("perceived_severity", alarm.getSeverity())
                .putParam("addition_text", alarm.getAdditionalText())
                .putParam("addition_information", alarm.getAdditionalInformation())
                .putParam("manufacturer", alarm.getManufacturer())
                .putParam("category", alarm.getCategory())
                .putParam("product_class", alarm.getProductClass())
                .putParam("notification_type", alarm.getNotificationType())
                .putParam("event_time", alarm.getEventTime().getTime())
                .putParam("detection_time", alarm.getDetectionTime().getTime())
                .putParam("manager_object_instance", alarm.getManagedObjectInstance())
                .putParam("controller_serial_number", alarm.getControllerSerial())
                .putParam("pppoe_account", alarm.getPppoeAccount());
    }

    private static class GRecord extends GenericData.Record {
        public GRecord(Schema schema) { super(schema); }
        public GRecord putParam(String key, Object value) {
            this.put(key, value);
            return this;
        }
        public GRecord putParamIgnoreNull(String key, Object value) {
            if (!Objects.equals(null, value))
                this.put(key, value);
            return this;
        }
    }
}
