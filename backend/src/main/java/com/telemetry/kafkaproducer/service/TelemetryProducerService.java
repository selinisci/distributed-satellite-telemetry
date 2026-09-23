package com.telemetry.kafkaproducer.service;

import com.telemetry.kafkaproducer.dto.TelemetryData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class TelemetryProducerService {
    private static final String TOPIC = "satellite-telemetry-topic";
    private final KafkaTemplate<String, TelemetryData> kafkaTemplate;
    private final Random random = new Random();

    public TelemetryProducerService(KafkaTemplate<String, TelemetryData> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    //the method is triggered automatically every 1000 milliseconds
    @Scheduled(fixedRate = 1000)
    public void generateAndSendData(){
        double mockTemperature = 15.0 + (30.0 * random.nextDouble());
        double mockSignal = 50.0 + (50.0 * random.nextDouble());

        // 0.0 to 100.0 percent
        double mockBattery = 100.0 * random.nextDouble();

        // 24.0 to 28.0 Volts
        double mockVoltage = 24.0 + (4.0 * random.nextDouble());

        // 95% probability of being true (satellite is mostly operational)
        boolean mockOperational = random.nextDouble() > 0.05;

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        // Ensure the TelemetryData constructor matches this new signature
        TelemetryData data = new TelemetryData("SAT_TR_01", mockTemperature, mockSignal, timestamp, mockBattery, mockVoltage, mockOperational);
        kafkaTemplate.send(TOPIC, data.getSatelliteID(), data);

        System.out.println("Data sent to Kafka: " + data.getSatelliteID() + " at " + data.getTimestamp());
    }




}
