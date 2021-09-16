package com.digitalnx.resource.api.relay.relaygroup;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RelayGroupController {
    RelayGroupRepository repository;
    RelayGroupModelAssembler assembler;
    public RelayGroupController(RelayGroupRepository repository, RelayGroupModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    @GetMapping("/relays")
    public CollectionModel<EntityModel<RelayGroup>> all() {
        List<EntityModel<RelayGroup>> relayGroup = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(relayGroup, linkTo(methodOn(RelayGroupController.class).all()).withSelfRel());
    }

    @GetMapping("/relays/{id}")
    public EntityModel<RelayGroup> one(@PathVariable Integer id) {
        RelayGroup relayGroup = repository.findById(id).orElseThrow(() -> { return new RelayGroupNotFoundException(id); });
        return assembler.toModel(relayGroup);
    }
}
