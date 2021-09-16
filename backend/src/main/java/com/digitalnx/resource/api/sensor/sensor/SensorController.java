package com.digitalnx.resource.api.sensor.sensor;

import net.minidev.json.JSONObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SensorController {
    private final SensorRepository repository;
    private final SensorModelAssembler assembler;
    public SensorController(SensorRepository repository, SensorModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/sensor/all")
    public CollectionModel<EntityModel<Sensor>> allSensors() {
        List<EntityModel<Sensor>> sensors = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(sensors, linkTo(methodOn(SensorController.class).allSensors()).withSelfRel());
    }

    @GetMapping("/sensor/{id}")
    public EntityModel<Sensor> one(@RequestParam Integer id) {
        Sensor sensor = repository.findById(id).orElseThrow( () -> new SensorNotFoundException(id) );
        return assembler.toModel(sensor);
    }

    @GetMapping("/sensor/{id}/data")
    public ResponseEntity<Object> getData(@PathVariable Integer id) throws IOException {
        JSONObject res = new JSONObject();
        try {
            Sensor sensor = repository.findById(id).orElseThrow(() -> { return new SensorNotFoundException(id); });
            res.put("result", "Success.");
            res.put("value", sensor.getValue());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch(Exception e) {
            res.put("result", "Error!");
            return new ResponseEntity<>(res, HttpStatus.METHOD_NOT_ALLOWED);
        }
   }
}
