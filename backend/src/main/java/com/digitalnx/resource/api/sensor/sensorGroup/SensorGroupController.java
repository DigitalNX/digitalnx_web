package com.digitalnx.resource.api.sensor.sensorGroup;

import com.digitalnx.resource.api.relay.relaygroup.RelayGroupController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SensorGroupController {
    private final SensorGroupRepository repository;
    private final SensorGroupModelAssembler assembler;
    public SensorGroupController(SensorGroupRepository repository, SensorGroupModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/sensors")
    public CollectionModel<EntityModel<SensorGroup>> all() {
        List<EntityModel<SensorGroup>> sensorGroup = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(sensorGroup, linkTo(methodOn(RelayGroupController.class).all()).withSelfRel());
    }

    @GetMapping("/sensors/{id}")
    public EntityModel<SensorGroup> one(@PathVariable Integer id) {
        SensorGroup sensorGroup = repository.findById(id).orElseThrow(() -> new SensorGroupNotFoundException(id));
        return assembler.toModel(sensorGroup);
    }
}
