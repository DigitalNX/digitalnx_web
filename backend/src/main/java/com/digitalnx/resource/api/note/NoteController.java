package com.digitalnx.resource.api.note;

import net.minidev.json.JSONObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class NoteController {
    NoteRepository repository;
    NoteModelAssembler assembler;
    public NoteController(NoteRepository repository, NoteModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    @GetMapping("/notes")
    CollectionModel<EntityModel<Note>> all() {
        List<EntityModel<Note>> notes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(notes,
                linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @PostMapping("/note/create")
    public ResponseEntity<Object> create(@RequestBody Note newNote) {
        JSONObject res = new JSONObject();
        try {
            repository.save(new Note(newNote.getTitle(), newNote.getBody()));
            res.put("result", "Success.");
            return new ResponseEntity<Object> (res, HttpStatus.OK);
        } catch(Exception e) {
            res.put("result", "Error!");
            return new ResponseEntity<Object> (res, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/note/{id}")
    public EntityModel<Note> one(@PathVariable Integer id) {
        Note note = repository.findById(id).orElseThrow(() -> { return new NoteNotFoundException(id); });
        return assembler.toModel(note);
    }

    @PostMapping("/note/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @RequestBody Note newNote) {
        JSONObject res = new JSONObject();
        try {
            Note note = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
            note.setTitle(newNote.getTitle());
            note.setBody(newNote.getBody());
            repository.save(note);
            res.put("result", "Success.");
            return new ResponseEntity<Object> (res, HttpStatus.OK);
        } catch(Exception e) {
            res.put("result", "Error!");
            return new ResponseEntity<Object> (res, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/note/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        JSONObject res = new JSONObject();
        try {
            repository.deleteById(id);

            res.put("result", "Success.");
            return new ResponseEntity<Object> (res, HttpStatus.OK);
        } catch(Exception e) {
            res.put("result", "Error!");
            return new ResponseEntity<Object> (res, HttpStatus.NOT_ACCEPTABLE);

        }
    }
}
