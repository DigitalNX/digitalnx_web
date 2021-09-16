package com.digitalnx.resource.api.info;

import com.digitalnx.resource.api.sensor.sensor.Sensor;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroupRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class InfoController {
    private final SensorGroupRepository sensorGroupRepo;
    public InfoController(SensorGroupRepository sensorGroupRepo) {
        this.sensorGroupRepo = sensorGroupRepo;
    }

    @GetMapping("/infoData")
    public ResponseEntity<Object> info() {
        try {
            JSONObject output = new JSONObject();
            output.put("title", "Info Box");
            output.put("info_id", 1);
            List<SensorGroup> sensorGroups = sensorGroupRepo.findAll();
            JSONArray infoBody = new JSONArray();
            for (SensorGroup sensors : sensorGroups) {
                JSONObject infoItem = new JSONObject();
                infoItem.put("title", sensors.getName());
                infoItem.put("type", InfoboxContentType.SENSOR.name());
                JSONArray tmpContent = new JSONArray();
                for (Sensor sensor : sensors.getSensors()) {
                    JSONObject tmp = new JSONObject();
                    tmp.put("device_name", sensor.getDeviceName());
                    tmp.put("sensor_type", sensor.getSensorType().name());
                    tmp.put("value", sensor.getValue());
                    tmpContent.appendElement(tmp);
                    }
                infoItem.put("content", tmpContent);
                infoBody.appendElement(infoItem);
                }

            JSONObject infoItem = new JSONObject();
            infoItem.put("title", "Website Statistics");
            infoItem.put("type", InfoboxContentType.RAW_STRING.name());
            List<String> tmp = Arrays.asList("Today's visitors: 2", "Total visitors: 4", "Today's comments: 3", "Total comments: 6");
            JSONArray arTmp = new JSONArray();
            arTmp.addAll(tmp);
            infoItem.put("content", arTmp);
            infoBody.appendElement(infoItem);

            infoItem = new JSONObject();
            infoItem.put("title", "Social Network Statistics");
            infoItem.put("type", InfoboxContentType.RAW_STRING.name());
            tmp = Arrays.asList("Today's liked posts: 4", "Total liked posts: 15", "Today's comments: 5", "Total comments: 4");
            arTmp = new JSONArray();
            arTmp.addAll(tmp);
            infoItem.put("content", arTmp);
            infoBody.appendElement(infoItem);

            output.put("items", infoBody);
            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (Exception e) {
            JSONObject output = new JSONObject();
            output.put("result", "Error!");
            return new ResponseEntity<>(output, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}

