package com.digitalnx.resource.api.weatherforecast;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WeatherForecast {
    @Id
    @GeneratedValue
    private Integer id;
    private String baseUrl;
    private String apiToken;
    private String location;

    private WeatherForecast() {}

    public WeatherForecast(String baseUrl, String apiToken, String location) {
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
        this.location = location;
    }

    public String getApiUrl(int numberOfDays) { return String.format(getBaseUrl(), getLocation(), numberOfDays, getApiToken()); }
    public String getApiUrl() { return getApiUrl(7); }
    public String getBaseUrl() { return baseUrl; }
    public String getApiToken() { return apiToken; }
    public String getLocation() {
        return location;
    }
    public Integer getId() { return id; }
    public void setBaseUrl(String apiUrl) { this.baseUrl = apiUrl; }
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
    public void setLocation(String location) { this.location = location; }
}
