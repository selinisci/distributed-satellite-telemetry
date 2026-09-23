package com.telemetry.kafkaproducer.dto;

public class TelemetryData {
    private String satelliteID;
    private double temperature;
    private double signalStrength;
    private String timestamp;
    private double batteryLevel;
    private double systemVoltage;
    private boolean isOperational;

    public TelemetryData(String satelliteID, double temperature, double signalStrength, String timestamp, double batteryLevel, double systemVoltage, boolean isOperational) {
        this.satelliteID = satelliteID;
        this.temperature = temperature;
        this.signalStrength = signalStrength;
        this.timestamp = timestamp;
        this.batteryLevel = batteryLevel;
        this.systemVoltage = systemVoltage;
        this.isOperational = isOperational;
    }


    public TelemetryData(){}

    public String getSatelliteID() {
        return satelliteID;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setSatelliteID(String satelliteID) {
        this.satelliteID = satelliteID;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
}



