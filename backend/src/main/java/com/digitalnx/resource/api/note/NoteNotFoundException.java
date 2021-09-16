package com.digitalnx.resource.api.note;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Integer id) { super("Note with id " + id + " not found!"); }
}
