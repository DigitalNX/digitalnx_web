package com.digitalnx.resource.api.relay.relay;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RelayController {
    private final RelayRepository repository;
    private final RelayModelAssembler assembler;
    public RelayController(RelayRepository repository, RelayModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/relay/all")
    public CollectionModel<EntityModel<Relay>> all() {
        List<EntityModel<Relay>> relays = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(relays, linkTo(methodOn(RelayController.class).all()).withSelfRel());
    }

    @GetMapping("/relay/{id}")
    public EntityModel<Relay> one(@PathVariable Integer id) {
        Relay relay = repository.findById(id).orElseThrow(() -> new RelayNotFoundException(id));
        return assembler.toModel(relay);
    }

//    @PostMapping("/relay/add")
//    public String addNewRelay(@RequestParam String name, @RequestParam String address, @RequestParam RelayGroup relayGroup) {
//        Relay relay = new Relay(name, address, relayGroup);
//        repository.save(relay);
//        return "Saved";
//    }
    @GetMapping("/relay/{id}/{action}")
    public ResponseEntity<?> relayAction(@PathVariable Integer id, @PathVariable String action) {
        try {
            if(!(action.equals("on") || action.equals("off"))) {
                return ResponseEntity.badRequest().body("Invalid action commands.");
            } else {
                relayControl(id, action);
                return ResponseEntity.ok().body("Success.");
            }
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    private void relayControl(Integer relayId, String action) throws IOException {
        Relay relay = repository.findById(relayId).orElseThrow(() -> new RelayNotFoundException(relayId));
        String relayURL = "http://" + relay.getIpAddress() + relay.getControlRoute();

        URL url = new URL(relayURL + action);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/html");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();
    }
}
