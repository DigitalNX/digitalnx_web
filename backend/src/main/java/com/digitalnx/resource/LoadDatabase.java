package com.digitalnx.resource;

import com.digitalnx.resource.api.RepositoryManager;
import com.digitalnx.resource.api.info.Info;
import com.digitalnx.resource.api.layout.DashboardItem;
import com.digitalnx.resource.api.note.Note;
import com.digitalnx.resource.api.relay.relay.Relay;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroup;
import com.digitalnx.resource.api.sensor.SensorType;
import com.digitalnx.resource.api.sensor.sensor.Sensor;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import com.digitalnx.resource.api.settings.SecurityMode;
import com.digitalnx.resource.api.settings.Settings;
import com.digitalnx.resource.api.settings.UILanguage;
import com.digitalnx.resource.api.weatherforecast.WeatherForecast;
import com.digitalnx.resource.auth.AuthService;
import com.digitalnx.resource.auth.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Value("${weather.apiBaseUrl}")
    private String weatherBaseUrl;

    @Value("${weather.apiToken}")
    private String weatherApiToken;

    @Value("${weather.location}")
    private String weatherLocation;

    @Bean
    CommandLineRunner initDatabase(RepositoryManager repo, AuthService auth) {
        return args -> {
            repo.clearData();
            auth.clearData();

            RelayGroup relayGroup = new RelayGroup("Bedroom Relays");
            log.info("Adding a Relay group: " + repo.addRelayGroup(relayGroup));
            for(int i = 0; i < 5; i++) {
                log.info("Adding a Relay: " + repo.addRelay(new Relay("192.168.1.1" + i, "Device " + i, relayGroup)));
            }

            SensorGroup sensorGroup = new SensorGroup("Garden Sensors");
            log.info("Adding a sensor group: " + repo.addSensorGroup(sensorGroup));
            log.info("Adding a sensor: " + repo.addSensor(new Sensor("192.168.1.20", "Device " + 1, SensorType.BOOLEAN, sensorGroup)));
            log.info("Adding a sensor: " + repo.addSensor(new Sensor("192.168.1.21", "Device " + 2, SensorType.FLOATING_POINT, sensorGroup)));

            Note note1 = new Note("Note 1", "Body");
            Note note2 = new Note("Note 2", "Body");
            log.info("Adding a new note: " + repo.addNote(note1));
            log.info("Adding a new note: " + repo.addNote(note2));

            WeatherForecast weatherForecast = new WeatherForecast(this.weatherBaseUrl, this.weatherApiToken, this.weatherLocation);
            repo.addWeather(weatherForecast);

            Info info = new Info();
            repo.addInfo(info);

            log.info("Adding weather forecast to dashboard: " + repo.addDashboardItem(new DashboardItem(weatherForecast)));
            log.info("Adding info widget to dashboard: " + repo.addDashboardItem(new DashboardItem(info)));
            log.info("Adding a relay group as a dashboard item: " + repo.addDashboardItem(new DashboardItem(relayGroup)));
            log.info("Adding a sensor group as a dashboard item: " + repo.addDashboardItem(new DashboardItem(sensorGroup)));
            log.info("Adding a note to dashboard: " + repo.addDashboardItem(new DashboardItem(note1)));
            log.info("Adding a note to dashboard: " + repo.addDashboardItem(new DashboardItem(note2)));

            log.info("Preloading " + repo.addSettings(new Settings("0.2", SecurityMode.NORMAL, UILanguage.EN)));
            auth.saveUser(new User("Iman F.", "admin", "password"));

        };
    }
}
