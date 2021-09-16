package com.digitalnx.resource.api.weatherforecast.weatherobjectmapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String cod;
    private List<Day> list;

    public String getCod() {
        return cod;
    }

    public List<Day> getList() {
        return list;
    }
}
