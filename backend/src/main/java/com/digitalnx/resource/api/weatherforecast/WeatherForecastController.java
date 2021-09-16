package com.digitalnx.resource.api.weatherforecast;

import com.digitalnx.resource.api.weatherforecast.weatherobjectmapper.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@RestController
public class WeatherForecastController {
    private final WeatherForecastRepository repository;
    private static final Logger log = LoggerFactory.getLogger(WeatherForecastController.class);
    Date date;

    public WeatherForecastController(WeatherForecastRepository repository)
    {
        this.repository = repository;
        this.date =  new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
    }

    @GetMapping("/weatherForecast")
    public EntityModel<Object> fetch() {
        JSONObject output = new JSONObject();

        String[] forecastDays = makeForecastDaysList(todayDateString());
        WeatherForecast weather = repository.findAll().get(0);
        output.put("location", weather.getLocation());
        output.put("date", new SimpleDateFormat("yyyy MMMMM dd").format(this.date));

        String urlPath = weather.getApiUrl();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL url = new URL(urlPath);
            Weather weatherMapper = objectMapper.readValue(url, Weather.class);
            JSONArray temperatureData = new JSONArray();
            for(int i = 0; i < forecastDays.length; i++) {
                JSONObject obj = new JSONObject();
                obj.put("temperature", weatherMapper.getList().get(i).getMain().get("temp"));
                obj.put("day", forecastDays[i]);
                temperatureData.appendElement(obj);
            }
            output.put("list", temperatureData);
        } catch(Exception e) {
            log.error("Error: Can't retrieve weather data.");
        }
        return EntityModel.of(output);

    }

    private String[] makeForecastDaysList(String today) {
        String[] days = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        if(!Arrays.asList(days).contains(today)) {
            throw new RuntimeException(String.format("Error: %s is an invalid day for makeForecastDaysList function.", today));
        }
        String[] res = new String[7];
        int todayIndex = Arrays.asList(days).indexOf(today);
        for(int i = 0; i < 7; i++) {
            res[i] = days[todayIndex % 7];
            todayIndex += 1;
        }
        return res;
    }

    private String GETRequestStr(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } else {
            throw new RuntimeException("Failed to retrieve weather forecast data.");
        }
    }

    private String todayDateString() {
        return new SimpleDateFormat("E").format(date).substring(0,3);
    }
}
