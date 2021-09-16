package com.digitalnx.resource.api.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}
