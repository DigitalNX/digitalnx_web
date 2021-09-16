package com.digitalnx.resource.api.weatherforecast;

public class WeatherForecastNotAvailableException extends RuntimeException {
    public WeatherForecastNotAvailableException() { super("Weather data retrieval error!"); }
}
