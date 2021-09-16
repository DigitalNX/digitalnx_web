package com.digitalnx.resource.api.sensor.sensorGroup;

import com.digitalnx.resource.api.layout.DashboardItem;
import com.digitalnx.resource.api.sensor.sensor.Sensor;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SensorGroup {
    @Id @GeneratedValue private Integer id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="sensorGroup", cascade = CascadeType.REMOVE)
    private Set<Sensor> sensors;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<DashboardItem> dashboardItems;

    public SensorGroup(String name) {
        setName(name);
    }

    SensorGroup(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Sensor> getSensors() {
        return sensors;
    }
    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }
}
