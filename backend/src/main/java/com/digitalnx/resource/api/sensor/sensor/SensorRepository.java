package com.digitalnx.resource.api.sensor.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}
