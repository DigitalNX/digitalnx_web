package com.digitalnx.resource.api.layout;

import com.digitalnx.resource.api.info.Info;
import com.digitalnx.resource.api.note.Note;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroup;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import com.digitalnx.resource.api.weatherforecast.WeatherForecast;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class DashboardItem {
    @Id @GeneratedValue private Integer id;
    private WidgetType widgetType;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WeatherForecast weatherForecast;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Info info;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RelayGroup relayGroup;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SensorGroup sensorGroup;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Note note;

    DashboardItem() {}

//    public static Map<WidgetType, Class<?>> getWidgetTypeToClassMap() {
//        Map<WidgetType, Class<?>> map = new HashMap<>();
//        map.put(WidgetType.RELAY_GROUP, RelayGroupRepository.class);
//        map.put(WidgetType.SENSOR_GROUP, SensorGroupRepository.class);
//        return map;
//    }

    public DashboardItem(RelayGroup relayGroup) {
        this.widgetType = WidgetType.RELAY_GROUP;
        this.relayGroup = relayGroup;
    }
    public DashboardItem(SensorGroup sensorGroup) {
        this.widgetType = WidgetType.SENSOR_GROUP;
        this.sensorGroup = sensorGroup;
    }

    public DashboardItem(Note note) {
        this.widgetType = WidgetType.NOTE;
        this.note = note;
    }

    public DashboardItem(WeatherForecast weatherForecast) {
        this.widgetType = WidgetType.WEATHER;
        this.weatherForecast = weatherForecast;
    }

    public DashboardItem(Info info) {
        this.widgetType = WidgetType.INFO;
        this.info = info;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public RelayGroup getRelayGroup() { return relayGroup; }
    public SensorGroup getSensorGroup() { return sensorGroup; }
    public Note getNote() { return note; }
    public WidgetType getWidgetType() { return widgetType; }
    public WeatherForecast getWeatherForecast() { return weatherForecast; }
    public Info getInfo() { return info; }
}
