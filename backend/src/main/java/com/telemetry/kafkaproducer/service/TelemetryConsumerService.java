package com.telemetry.kafkaproducer.service;

import com.telemetry.kafkaproducer.dto.TelemetryData;
import com.telemetry.kafkaproducer.entity.TelemetryEntity;
import com.telemetry.kafkaproducer.repository.TelemetryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TelemetryConsumerService {
    private final TelemetryRepository telemetryRepository;

    public TelemetryConsumerService(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }
    //listens to the specified topic and automatically triggers the method when new data arrives
    @KafkaListener(topics = "satellite-telemetry-topic", groupId = "telemetry-group")
    public void consumeTelemetryData(TelemetryData data){
        System.out.println(">>> CONSUMER CAUGHT DATA: Satellite: " + data.getSatelliteID() +
                " | Temp: " + data.getTemperature() +
                " | Signal: " + data.getSignalStrength());
        TelemetryEntity entity = new TelemetryEntity();
        entity.setSatelliteID(data.getSatelliteID());
        entity.setTemperature(data.getTemperature());
        entity.setSignalStrength(data.getSignalStrength());
        entity.setTimestamp(Instant.now().toString());

        telemetryRepository.save(entity);
        System.out.println("--- DATA SAVED TO DATABASE SUCCESSFULLY ---");
    }
}
