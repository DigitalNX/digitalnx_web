package com.digitalnx.resource.api.sensor.sensor;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(Integer id) { super("Could not find sensor " + id); }
}
