package com.digitalnx.resource.api.sensor.sensorGroup;

public class SensorGroupNotFoundException extends RuntimeException {
    public SensorGroupNotFoundException(Integer id) {
        super("Could not find sensor " + id);
    }
}
