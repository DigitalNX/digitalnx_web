package com.digitalnx.resource.api.sensor.sensor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SensorModelAssembler implements RepresentationModelAssembler<Sensor, EntityModel<Sensor>> {
    @Override
    public EntityModel<Sensor> toModel(Sensor sensor) {
        return EntityModel.of(sensor,
                linkTo(methodOn(SensorController.class).allSensors()).withRel("sensors"),
                linkTo(methodOn(SensorController.class).one(sensor.getId())).withSelfRel());
    }
}
