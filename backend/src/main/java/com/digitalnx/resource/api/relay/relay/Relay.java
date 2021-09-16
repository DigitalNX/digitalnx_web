package com.digitalnx.resource.api.relay.relay;

import com.digitalnx.resource.api.relay.relaygroup.RelayGroup;

import javax.persistence.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Entity
public class Relay {
    @Id @GeneratedValue()
    Integer id;
    private String ipAddress;
    private String deviceName;
    private String controlRoute;
    private String statusRoute;
    private Boolean isControlledByScheduler;
    @ManyToOne(fetch=FetchType.LAZY) private RelayGroup relayGroup;

    Relay() {}

    public Relay(String ipAddress, String deviceName, RelayGroup relayGroup,
                 String controlRoute, String statusRoute, Boolean isControlledByScheduler) {
        setIpAddress(ipAddress);
        setDeviceName(deviceName);
        setControlRoute(controlRoute);
        setStatusRoute(statusRoute);
        setControlledByScheduler(isControlledByScheduler);
        setRelayGroup(relayGroup);
    }

    public Relay(String ipAddress, String deviceName, RelayGroup relayGroup) {
        this(ipAddress, deviceName, relayGroup, "RELAY", "STATUS", false);
    }

    public void setRelayGroup(RelayGroup relayGroup) {
        this.relayGroup = relayGroup;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) { // TODO validate ip address
        this.ipAddress = ipAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getControlRoute() {
        return controlRoute;
    }

    public void setControlRoute(String controlRoute) {
        this.controlRoute = controlRoute;
    }

    public String getStatusRoute() {
        return statusRoute;
    }

    public void setStatusRoute(String statusRoute) {
        this.statusRoute = statusRoute;
    }

    public Boolean getControlledByScheduler() {
        return isControlledByScheduler;
    }

    public void setControlledByScheduler(Boolean controlledByScheduler) {
        isControlledByScheduler = controlledByScheduler;
    }

    public boolean getStatus() {
        String relayStatusUrl = "http://" + this.ipAddress + "/" + this.getStatusRoute();
        try {
            URL url = new URL(relayStatusUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "text/html");
            con.setConnectTimeout(150);
            con.setReadTimeout(150);
            InputStream inputStream = con.getInputStream();
            return Boolean.parseBoolean(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
        } catch(Exception e) {
            return false;
        }
    }

    public Integer getGroupId() {
        return this.relayGroup.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.deviceName, this.ipAddress);
    }

    @Override
    public String toString() {
        return String.format("[Relay] %s : %s", this.deviceName, this.ipAddress);
    }
    public Integer getId() { return this.id; }
}
