package com.telemetry.kafkaproducer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "satellite_metrics")
public class TelemetryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String satelliteID;
    private double temperature;
    private double signalStrength;
    private String timestamp;
    private double batteryLevel;
    private double systemVoltage;
    private boolean isOperational;

    public TelemetryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSatelliteID() {
        return satelliteID;
    }

    public void setSatelliteID(String satelliteID) {
        this.satelliteID = satelliteID;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getSystemVoltage() {
        return systemVoltage;
    }

    public void setSystemVoltage(double systemVoltage) {
        this.systemVoltage = systemVoltage;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
