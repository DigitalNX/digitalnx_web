package com.digitalnx.resource.api.relay.relay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelayRepository extends JpaRepository<Relay, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}
