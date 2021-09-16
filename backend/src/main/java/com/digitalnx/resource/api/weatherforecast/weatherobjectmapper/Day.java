package com.digitalnx.resource.api.weatherforecast.weatherobjectmapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    private Integer dt;
    private Map<String, Double> main;

    public Integer getDt() {
        return dt;
    }

    public Map<String, Double> getMain() {
        return main;
    }
}
