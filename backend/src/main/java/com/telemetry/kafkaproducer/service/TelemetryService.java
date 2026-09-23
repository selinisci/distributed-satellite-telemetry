package com.telemetry.kafkaproducer.service;

import com.telemetry.kafkaproducer.entity.TelemetryEntity;
import com.telemetry.kafkaproducer.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;

    @Autowired
    public TelemetryService(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    public List<TelemetryEntity> getHistoricalData(String satelliteId) {
        return telemetryRepository.findTop100BySatelliteIDOrderByTimestampDesc(satelliteId);
    }
}