package com.digitalnx.resource.api.relay.relay;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class RelayModelAssembler implements RepresentationModelAssembler<Relay, EntityModel<Relay>> {
    @Override
    public EntityModel<Relay> toModel(Relay entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RelayController.class).one(entity.getId())).withSelfRel());
    }
}
