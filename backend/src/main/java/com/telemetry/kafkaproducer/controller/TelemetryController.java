package com.telemetry.kafkaproducer.controller;

import com.telemetry.kafkaproducer.entity.TelemetryEntity;
import com.telemetry.kafkaproducer.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    @Autowired
    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    // Endpoint to retrieve historical data for the dashboard
    @GetMapping("/history/{satelliteId}")
    public ResponseEntity<?> getHistoricalData(@PathVariable String satelliteID) {
        try {
            List<TelemetryEntity> history = telemetryService.getHistoricalData(satelliteID);

            if (history.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(history);
        }catch (Exception e){
            String errorMessage = "SYSTEM CRASH REASON " + e.getMessage();
            if (e.getCause() != null){
                errorMessage += " | CAUSE: " + e.getCause().getMessage();
            }
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

}