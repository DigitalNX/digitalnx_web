package com.digitalnx.resource.api;

import com.digitalnx.resource.api.info.Info;
import com.digitalnx.resource.api.info.InfoNotFoundException;
import com.digitalnx.resource.api.info.InfoRepository;
import com.digitalnx.resource.api.layout.DashboardItem;
import com.digitalnx.resource.api.layout.DashboardItemNotFoundException;
import com.digitalnx.resource.api.layout.DashboardItemRepository;
import com.digitalnx.resource.api.layout.WidgetType;
import com.digitalnx.resource.api.note.Note;
import com.digitalnx.resource.api.note.NoteNotFoundException;
import com.digitalnx.resource.api.note.NoteRepository;
import com.digitalnx.resource.api.relay.relay.Relay;
import com.digitalnx.resource.api.relay.relay.RelayRepository;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroup;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroupNotFoundException;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroupRepository;
import com.digitalnx.resource.api.sensor.sensor.Sensor;
import com.digitalnx.resource.api.sensor.sensor.SensorNotFoundException;
import com.digitalnx.resource.api.sensor.sensor.SensorRepository;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroupNotFoundException;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroupRepository;
import com.digitalnx.resource.api.settings.Settings;
import com.digitalnx.resource.api.settings.SettingsNotFoundException;
import com.digitalnx.resource.api.settings.SettingsRepository;
import com.digitalnx.resource.api.weatherforecast.WeatherForecast;
import com.digitalnx.resource.api.weatherforecast.WeatherForecastNotAvailableException;
import com.digitalnx.resource.api.weatherforecast.WeatherForecastRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RepositoryManager {
    private final RelayRepository relayRepo;
    private final RelayGroupRepository relayGroupRepo;
    private final SensorRepository sensorRepo;
    private final SensorGroupRepository sensorGroupRepo;
    private final SettingsRepository settingsRepo;
    private final DashboardItemRepository dashboardItemRepo;
    private final NoteRepository noteRepo;
    private final WeatherForecastRepository weatherRepo;
    private final InfoRepository infoRepo;
    List<JpaRepository<?, Integer>> repositoryList;

    public RepositoryManager(RelayRepository relayRepo, RelayGroupRepository relayGroupRepo,
                             SensorRepository sensorRepo, SensorGroupRepository sensorGroupRepo,
                             NoteRepository noteRepo, InfoRepository infoRepo,
                             SettingsRepository settingsRepo, DashboardItemRepository dashboardItemRepo,
                             WeatherForecastRepository weatherRepo) {
        this.repositoryList = new ArrayList<>();
        repositoryList.add(relayRepo);
        repositoryList.add(relayGroupRepo);
        repositoryList.add(sensorRepo);
        repositoryList.add(sensorGroupRepo);
        repositoryList.add(noteRepo);
        repositoryList.add(infoRepo);
        repositoryList.add(settingsRepo);
        repositoryList.add(weatherRepo);
        repositoryList.add(dashboardItemRepo);

        this.relayRepo = relayRepo;
        this.relayGroupRepo = relayGroupRepo;
        this.sensorRepo = sensorRepo;
        this.sensorGroupRepo = sensorGroupRepo;
        this.noteRepo = noteRepo;
        this.infoRepo = infoRepo;
        this.settingsRepo = settingsRepo;
        this.dashboardItemRepo = dashboardItemRepo;
        this.weatherRepo = weatherRepo;
    }

    public Map<WidgetType, List<Integer>> getAllWidgets() {
        return Map.of(
                WidgetType.RELAY_GROUP, relayGroupRepo.getAllIds(),
                WidgetType.SENSOR_GROUP, sensorGroupRepo.getAllIds(),
                WidgetType.NOTE, noteRepo.getAllIds(),
                WidgetType.WEATHER, weatherRepo.getAllIds(),
                WidgetType.INFO, infoRepo.getAllIds()
        );
    }

    public void clearData() {
        for (var repo : this.repositoryList) {
            repo.deleteAllInBatch();
        }
    }

    public List<Relay> getAllRelays() { return relayRepo.findAll(); }
    public List<RelayGroup> getAllRelayGroups() { return relayGroupRepo.findAll(); }
    public List<Sensor> getAllSensors() { return sensorRepo.findAll(); }
    public List<SensorGroup> getAllSensorGroups() { return sensorGroupRepo.findAll(); }
    public List<Note> getAllNotes() { return noteRepo.findAll(); }
    public List<DashboardItem> getAllDashboardItems() { return dashboardItemRepo.findAll(); }
    public List<Settings> getAllSettings() { return settingsRepo.findAll(); }
    public List<Info> getAllInfo() { return infoRepo.findAll(); }
    public List<WeatherForecast> getAllWeatherObjects() { return weatherRepo.findAll(); }

    public Optional<Relay> getRelay(Integer id) { return relayRepo.findById(id); }
    public RelayGroup getRelayGroup(Integer id) {  return relayGroupRepo.findById(id).orElseThrow(() -> new RelayGroupNotFoundException(id)); }
    public Sensor getSensor(Integer id) { return sensorRepo.findById(id).orElseThrow(() -> new SensorNotFoundException(id)); }
    public SensorGroup getSensorGroup(Integer id) { return sensorGroupRepo.findById(id).orElseThrow(() -> new SensorGroupNotFoundException(id)); }
    public Note getNote(Integer id) { return noteRepo.findById(id).orElseThrow(() -> new NoteNotFoundException(id)); }
    public DashboardItem getDashboardItem(Integer id) { return dashboardItemRepo.findById(id).orElseThrow(() -> new DashboardItemNotFoundException(id)); }
    public Settings getSetting(Integer id) { return settingsRepo.findById(id).orElseThrow(() -> new SettingsNotFoundException()); }
    public Info getInfo(Integer id) { return infoRepo.findById(id).orElseThrow(() -> new InfoNotFoundException()); }
    public WeatherForecast getWeatherObject(Integer id) { return weatherRepo.findById(id).orElseThrow(() -> new WeatherForecastNotAvailableException()); }

    public Relay addRelay(Relay relay) { return relayRepo.save(relay); }
    public RelayGroup addRelayGroup(RelayGroup relayGroup) { return relayGroupRepo.save(relayGroup); }
    public Sensor addSensor(Sensor sensor) { return sensorRepo.save(sensor); }
    public SensorGroup addSensorGroup(SensorGroup sensorGroup) { return sensorGroupRepo.save(sensorGroup); }
    public Note addNote(Note note) { return noteRepo.save(note); }
    public DashboardItem addDashboardItem(DashboardItem dashboardItem) { return dashboardItemRepo.save(dashboardItem); }
    public Settings addSettings(Settings settings) { return settingsRepo.save(settings); }
    public Info addInfo(Info info) { return infoRepo.save(info); }
    public WeatherForecast addWeather(WeatherForecast weatherForecast) { return weatherRepo.save(weatherForecast); }
}