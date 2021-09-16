package com.digitalnx.resource.api.weatherforecast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    public List<Integer> getAllIds();
}
