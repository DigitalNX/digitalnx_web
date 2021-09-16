package com.digitalnx.resource.api.sensor.sensorGroup;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SensorGroupModelAssembler implements RepresentationModelAssembler<SensorGroup, EntityModel<SensorGroup>> {
    @Override
    public EntityModel<SensorGroup> toModel(SensorGroup sensorGroup) {
        return EntityModel.of(sensorGroup,
                linkTo(methodOn(SensorGroupController.class).one(sensorGroup.getId())).withSelfRel());
    }
}
