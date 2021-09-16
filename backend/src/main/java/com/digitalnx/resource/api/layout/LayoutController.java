package com.digitalnx.resource.api.layout;

import com.digitalnx.resource.api.RepositoryManager;
import com.digitalnx.resource.api.note.Note;
import com.digitalnx.resource.api.relay.relaygroup.RelayGroup;
import com.digitalnx.resource.api.sensor.sensorGroup.SensorGroup;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LayoutController {
    private final DashboardItemRepository repository;
    private final RepositoryManager repositoryManager;
    private final Map<WidgetType, String> widgetToString;

    public LayoutController(DashboardItemRepository repository, RepositoryManager repositoryManager) {
        this.repository = repository;
        this.repositoryManager = repositoryManager;

        widgetToString = Map.of(
                WidgetType.RELAY_GROUP, "Relay Group",
                WidgetType.SENSOR_GROUP, "Sensor Group",
                WidgetType.NOTE, "Note",
                WidgetType.INFO, "Info widget",
                WidgetType.WEATHER, "Weather"
        );
    }

    // TODO rewrite this
    @GetMapping("/layout")
    public CollectionModel<JSONObject> layout() {
        List<DashboardItem> items = repository.findAll();
        List<JSONObject> entities = new ArrayList<>();
        for (DashboardItem i : items) {
            WidgetType widgetType = i.getWidgetType();
            JSONObject entity = new JSONObject();
            entity.put("dashboardId", i.getId());
            entity.put("widgetType", widgetType);
            entity.put("name", widgetToString.get(widgetType));
            if (widgetType == WidgetType.RELAY_GROUP) {
                entity.put("id", i.getRelayGroup().getId());
                entities.add(entity);
            } else if (widgetType == WidgetType.SENSOR_GROUP) {
                entity.put("id", i.getSensorGroup().getId());
                entities.add(entity);
            } else if (widgetType == WidgetType.NOTE) {
                entity.put("id", i.getNote().getId());
                entities.add(entity);
            } else if (widgetType == WidgetType.WEATHER) {
                entity.put("id", i.getWeatherForecast().getId());
                entities.add(entity);
            } else if (widgetType == WidgetType.INFO) {
                entity.put("id", i.getInfo().getId());
                entities.add(entity);
            }
        }
        return CollectionModel.of(entities);
    }

    @PostMapping("/layout/new")
    public ResponseEntity<Object> add(@RequestBody Widget widget) {
        JSONObject res = new JSONObject();
        try {
            WidgetType item = WidgetType.valueOf(widget.getItemName());
            Integer id = widget.getId();
            if (item == WidgetType.RELAY_GROUP) {
                RelayGroup relayGroup = repositoryManager.getRelayGroup(id);
                repository.save(new DashboardItem(relayGroup));
            } else if (item == WidgetType.SENSOR_GROUP) {
                SensorGroup sensorGroup = repositoryManager.getSensorGroup(id);
                repository.save(new DashboardItem(sensorGroup));
            } else if (item == WidgetType.NOTE) {
                Note note = repositoryManager.getNote(id);
                repository.save(new DashboardItem(note));
            }
            res.put("result", "Success.");
            return new ResponseEntity<Object>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.put("result", "Error.");
            return new ResponseEntity<Object>(res, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/layout/delete/{dashboardId}")
    public ResponseEntity<Object> delete(@PathVariable Integer dashboardId) {
        JSONObject res = new JSONObject();
        try {
            repository.deleteById(dashboardId);
            res.put("result", "Success.");
            return new ResponseEntity<Object>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.put("result", "Error!");
            return new ResponseEntity<Object>(res, HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("/widgets")
    public CollectionModel<Object> widgets() {
        JSONArray output = new JSONArray();
        JSONObject entry;
        var widgets = repositoryManager.getAllWidgets();
        for (var w : widgets.entrySet()) {
            for(int i = 0; i < w.getValue().size(); i++) {
                entry = new JSONObject();
                entry.put("widgetType", w.getKey());
                entry.put("name", widgetToString.get(w.getKey()));
                entry.put("id", w.getValue().get(i));
                output.add(entry);
            }
        }
        return CollectionModel.of(output);
    }
}