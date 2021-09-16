package com.digitalnx.resource.api.sensor.sensor;

import com.digitalnx.resource.api.sensor.SensorType;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Entity
public class Sensor {
    @Id @GeneratedValue private Integer id;
    private String deviceName;
    private String ipAddress;
    private SensorType sensorType;
    private String dataRoute;
    @ManyToOne(fetch=FetchType.LAZY) private SensorGroup sensorGroup;
    private Boolean isAffectedBySecurityMode;

    Sensor() {}

    public Sensor(String ipAddress, String deviceName, SensorType sensorType, SensorGroup sensorGroup,
                  String dataRoute, Boolean isAffectedBySecurityMode) {
        setIpAddress(ipAddress);
        setDeviceName(deviceName);
        setSensorType(sensorType);
        setSensorGroup(sensorGroup);
        setDataRoute(dataRoute);
        setAffectedBySecurityMode(isAffectedBySecurityMode);
    }
    public Sensor(String ipAddress, String deviceName, SensorType sensorType, SensorGroup sensorGroup) {
        this(ipAddress, deviceName, sensorType, sensorGroup, "DATA", false);
    }

    public String getValue() throws IOException {
        String sensorUrl = "http://" + this.ipAddress + "/" + this.dataRoute;
        try {
            URL url = new URL(sensorUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "text/html");
            con.setConnectTimeout(100);
            con.setReadTimeout(100);
            InputStream inputStream = con.getInputStream();
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch(Exception e) {
            return "INVALID";
        }
    }

    @Override
    public String toString() {
        return String.format("[Sensor] %s : %s", this.deviceName, this.ipAddress);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDataRoute() {
        return dataRoute;
    }

    public void setDataRoute(String dataRoute) {
        this.dataRoute = dataRoute;
    }

    public Boolean getAffectedBySecurityMode() {
        return isAffectedBySecurityMode;
    }

    public Integer getGroupId() { return this.sensorGroup.getId(); }

    public void setSensorGroup(SensorGroup sensorGroup) {
        this.sensorGroup = sensorGroup;
    }

    public void setAffectedBySecurityMode(Boolean affectedBySecurityMode) {
        isAffectedBySecurityMode = affectedBySecurityMode;
    }
// TODO // Implement the following method
//    boolean booleanFromDouble(double threshold) throws IOException {
//        if(this.getSensorType() != SensorType.FLOATING_POINT){
//            throw new RuntimeException("Invalid sensor type!");
//        }
//        double value = Double.parseDouble(this.getData());
//        return value > threshold;
//    }
//
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.deviceName, this.ipAddress);
    }
}