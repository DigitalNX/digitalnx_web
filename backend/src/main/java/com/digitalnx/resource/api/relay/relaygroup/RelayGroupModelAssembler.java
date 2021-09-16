package com.digitalnx.resource.api.relay.relaygroup;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RelayGroupModelAssembler implements RepresentationModelAssembler<RelayGroup, EntityModel<RelayGroup>> {
    @Override
    public EntityModel<RelayGroup> toModel(RelayGroup relayGroup) {
        return EntityModel.of(relayGroup,
                linkTo(methodOn(RelayGroupController.class).one(relayGroup.id)).withSelfRel());
    }
}
