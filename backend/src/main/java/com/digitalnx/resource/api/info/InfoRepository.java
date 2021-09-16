package com.digitalnx.resource.api.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}
